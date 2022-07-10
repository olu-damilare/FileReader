package com.dotpay.dotpay;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class StartUp implements ApplicationListener<ApplicationReadyEvent> {



    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {



    }
}

