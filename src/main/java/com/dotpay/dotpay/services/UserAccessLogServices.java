package com.dotpay.dotpay.services;

import com.dotpay.dotpay.enums.Duration;
import com.dotpay.dotpay.models.BlockedIP;
import com.dotpay.dotpay.models.UserAccessLog;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public interface UserAccessLogServices {

    void saveAccessLog(UserAccessLog accessLog);

    List<UserAccessLog> fetchBlockedIPAddresses(LocalDateTime start, LocalDateTime end, Duration duration, int limit);

}
