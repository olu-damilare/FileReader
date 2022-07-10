package com.dotpay.dotpay.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Table(name = "user_access_log")
@RequiredArgsConstructor
@Entity
public class UserAccessLog {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;

    private LocalDateTime date;

    @Column(name = "ip_address")
    private String ipAddress;

    private String request;

    @Column(name = "status_code")
    private String statusCode;

    @Lob
    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "request_count")
    private Long requestCount;
}
