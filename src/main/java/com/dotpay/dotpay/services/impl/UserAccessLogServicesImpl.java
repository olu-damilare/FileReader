package com.dotpay.dotpay.services.impl;

import com.dotpay.dotpay.enums.Duration;
import com.dotpay.dotpay.models.UserAccessLog;
import com.dotpay.dotpay.repository.UserAccessLogRepository;
import com.dotpay.dotpay.services.UserAccessLogServices;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserAccessLogServicesImpl implements UserAccessLogServices {

    private final UserAccessLogRepository userAccessLogRepository;

    public UserAccessLogServicesImpl(UserAccessLogRepository userAccessLogRepository) {
        this.userAccessLogRepository = userAccessLogRepository;
    }

    @Override
    public void saveAccessLog(UserAccessLog accessLog) {
         userAccessLogRepository.save(accessLog);
    }

    @Override
    public List<UserAccessLog> fetchBlockedIPAddresses(LocalDateTime start, LocalDateTime end, Duration duration, int limit) {
        return userAccessLogRepository.findUserAccessLogByDateBetweenAndLimit(start, end, limit);
    }

}
