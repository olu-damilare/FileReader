package com.dotpay.dotpay.dtos;

import com.dotpay.dotpay.enums.Duration;
import lombok.Data;

@Data
public class BlockedIPRequest {

    private Duration duration;
    private String start;
    private String end;
    private int limit;
}
