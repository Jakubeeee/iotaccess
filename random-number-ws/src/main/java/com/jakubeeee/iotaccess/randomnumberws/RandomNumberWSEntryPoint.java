package com.jakubeeee.iotaccess.randomnumberws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.jakubeeee.iotaccess.randomnumberws.RandomNumberApplicationConstants.ROOT_PACKAGE;

@SpringBootApplication(scanBasePackages = ROOT_PACKAGE)
public class RandomNumberWSEntryPoint {

    public static void main(String[] args) {
        SpringApplication.run(RandomNumberWSEntryPoint.class, args);
    }

}
