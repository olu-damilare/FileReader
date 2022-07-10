package com.dotpay.dotpay.services.impl;

import com.dotpay.dotpay.dtos.BlockedIPRequest;
import com.dotpay.dotpay.models.BlockedIP;
import com.dotpay.dotpay.models.UserAccessLog;
import com.dotpay.dotpay.repository.BlockedIPRepository;
import com.dotpay.dotpay.services.BlockedIPServices;
import com.dotpay.dotpay.services.UserAccessLogServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
@Slf4j
public class BlockedIPServicesImpl implements BlockedIPServices {

    private final BlockedIPRepository blockedIPRepository;

    private final UserAccessLogServices userAccessLogServices;

    public BlockedIPServicesImpl(BlockedIPRepository blockedIPRepository, UserAccessLogServices userAccessLogServices) {
        this.blockedIPRepository = blockedIPRepository;
        this.userAccessLogServices = userAccessLogServices;
    }


    @Override
    public void saveBlockedIPAddress(BlockedIP blockedIP) {
        blockedIPRepository.save(blockedIP);
    }

    @Override
    public void blockIPAddresses(BlockedIPRequest blockedIPRequest) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(blockedIPRequest.getStart(), format);
        boolean isHourlyDuration = blockedIPRequest.getDuration().name().equalsIgnoreCase("Hourly");

        // Given that the margin for each request is 1, set the end value to one hour or one day depending on the value
        // of the duration.

        LocalDateTime end = isHourlyDuration ?
                start.plus(1, ChronoUnit.HOURS)
                : start.plus(1, ChronoUnit.DAYS);

        List<UserAccessLog> userAccessLogList = userAccessLogServices.fetchBlockedIPAddresses(
                start, end, blockedIPRequest.getDuration(), blockedIPRequest.getLimit());

        if(userAccessLogList.isEmpty()){
            log.info("No IP address exceeded limit");
            return;
        }

        // Create a BlockedIP entity from the data obtained from userAccessLogList.

        userAccessLogList
                .forEach(userAccessLog -> {
                    System.out.println(userAccessLog.getIpAddress());
                    BlockedIP blockedIp = new BlockedIP();
                    blockedIp.setIpAddress(userAccessLog.getIpAddress());
                    blockedIp.setRequestNumber(userAccessLog.getId());
                    blockedIp.setComment("Exceeded " + blockedIPRequest.getDuration() + " limit of " + blockedIPRequest.getLimit());

                    blockedIPRepository.save(blockedIp);
                });

        log.info("Successfully fetched blocked IP addresses");
    }


}
