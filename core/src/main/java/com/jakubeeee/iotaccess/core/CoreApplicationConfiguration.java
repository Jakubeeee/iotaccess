package com.jakubeeee.iotaccess.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
@PropertySource("classpath:core.properties")
public class CoreApplicationConfiguration {

    @Bean
    public SchedulerFactoryBean scheduler() {
        return new SchedulerFactoryBean();
    }

}
