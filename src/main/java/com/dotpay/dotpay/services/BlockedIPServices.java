package com.dotpay.dotpay.services;

import com.dotpay.dotpay.dtos.BlockedIPRequest;
import com.dotpay.dotpay.enums.Duration;
import com.dotpay.dotpay.models.BlockedIP;

public interface BlockedIPServices {

    void saveBlockedIPAddress(BlockedIP blockedIP);

    void blockIPAddresses(BlockedIPRequest blockedIPRequest);

}
