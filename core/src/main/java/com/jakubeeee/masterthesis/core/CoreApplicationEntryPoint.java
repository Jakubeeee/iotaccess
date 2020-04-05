package com.jakubeeee.masterthesis.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import static com.jakubeeee.masterthesis.core.CoreApplicationConstants.ROOT_PACKAGE;

@SpringBootApplication(scanBasePackages = ROOT_PACKAGE)
@EntityScan(basePackages = ROOT_PACKAGE)
@EnableScheduling
public class CoreApplicationEntryPoint {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplicationEntryPoint.class, args);
    }

}
