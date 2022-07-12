package com.dotpay.dotpay.services.impl;

import com.dotpay.dotpay.dtos.BlockedIPRequest;
import com.dotpay.dotpay.enums.Duration;
import com.dotpay.dotpay.models.UserAccessLog;
import com.dotpay.dotpay.services.BlockedIPServices;
import com.dotpay.dotpay.services.UserAccessLogServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Service
@Slf4j
public class FileServices {

    private final UserAccessLogServices userAccessLogServices;
    private final BlockedIPServices blockedIPServices;
    private final ApplicationContext applicationContext;

    public FileServices(UserAccessLogServices userAccessLogServices, BlockedIPServices blockedIPServices, ApplicationContext applicationContext) {
        this.userAccessLogServices = userAccessLogServices;
        this.blockedIPServices = blockedIPServices;
        this.applicationContext = applicationContext;
    }

    public void loadUserAccessFileToDB(String... args){
        final String fileDateFormat = "yyyy-MM-dd HH:mm:ss.SSS";
        DateTimeFormatter format = DateTimeFormatter.ofPattern(fileDateFormat);
        Duration duration = null;
        int limit = 0;

        if(args[2].equalsIgnoreCase("Hourly")){
            duration = Duration.HOURLY;
            limit = 200;
        }else if(args[2].equalsIgnoreCase("Daily")){
            duration = Duration.DAILY;
            limit = 500;
        }else{
            throw new RuntimeException("Invalid duration. Duration must be either hourly or daily.");

        }

        try (InputStream is = new URL(args[0]).openStream()){
            loadDataToUserAccessLogTable(format, is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /* Set the duration enum and limit value depending on the
            value provided by the user.
         */



        initiateBlockIPRequest(duration, limit, args);
    }

    private void initiateBlockIPRequest(Duration duration, int limit, String[] args) {
        BlockedIPRequest blockedIPRequest = new BlockedIPRequest();
        blockedIPRequest.setDuration(duration);
        blockedIPRequest.setLimit(limit);
        blockedIPRequest.setStart(args[1]);

        log.info("About to fetch blocked IP addresses ============================================");
        blockedIPServices.blockIPAddresses(blockedIPRequest);
    }

    private void loadDataToUserAccessLogTable(DateTimeFormatter format, InputStream is) {
        Scanner scanner = new Scanner(is);

        /* Read each line of the text file and split by the delimiter.
           Create a UserAccessLog entity using the values obtained from each row,
           then save the entity to the user_access_log table.
         */
        while(scanner.hasNextLine()){
            String[] record = scanner.nextLine().split("\\|");
            LocalDateTime requestDate = LocalDateTime.parse(record[0], format);

            UserAccessLog userAccessLog = new UserAccessLog();
            userAccessLog.setDate(requestDate);
            userAccessLog.setIpAddress(record[1]);
            userAccessLog.setRequest(record[2]);
            userAccessLog.setStatusCode(record[3]);
            userAccessLog.setUserAgent(record[4]);

            userAccessLogServices.saveAccessLog(userAccessLog);

        }
    }
}
