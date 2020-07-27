package com.rmamedov.deasy.etlstarter.config;

import com.rmamedov.deasy.etlstarter.service.OrderEtlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class EtlConfig {

    @Bean
    @ConditionalOnMissingBean(OrderEtlService.class)
    public void onMissingEtlService() {
        log.error("Interface 'OrderEtlService' must be Implemented!");
        System.exit(1);
    }

}
