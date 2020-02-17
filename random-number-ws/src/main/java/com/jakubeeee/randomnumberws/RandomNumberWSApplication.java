package com.jakubeeee.randomnumberws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.jakubeeee.randomnumberws.RandomNumberApplicationConstants.ROOT_PACKAGE;

/**
 * Entry point of the <code>Random number web service</code> standalone module.
 */
@SpringBootApplication(scanBasePackages = ROOT_PACKAGE)
public class RandomNumberWSApplication {

    public static void main(String[] args) {
        SpringApplication.run(RandomNumberWSApplication.class, args);
    }

}
