package com.rmamedov.deasy.monitoringgood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;

@EnableBinding(Processor.class)
@SpringBootApplication
public class MonitoringGoodEtl {

    public static void main(String[] args) {
        SpringApplication.run(MonitoringGoodEtl.class, args);
    }

}
