package com.aurawave.controller.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class verifyValidityProductScheduler {
    @Scheduled(cron = "0 0 9 * * ?")
    public void verifyValidityProduct() {

    }
}
