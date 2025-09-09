package com.aurawave.controller.scheduler;

import com.aurawave.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VerifyValidityProductScheduler {

    @Autowired
    private ProductService service;

    @Scheduled(cron = "0 0 9 * * ?")
    public void verifyValidityProduct() {
        service.validValidityProduct();
    }
}
