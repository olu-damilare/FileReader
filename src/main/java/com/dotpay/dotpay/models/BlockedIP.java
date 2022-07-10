package com.dotpay.dotpay.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "blocked_ip")
@RequiredArgsConstructor
@Entity
public class BlockedIP {


    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "request_number")
    private Long requestNumber;
    private String comment;

}
