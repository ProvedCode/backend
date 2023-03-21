package com.provedcode.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@EnableConfigurationProperties
@PropertySource("pagination.properties")
@ConfigurationProperties(prefix = "page-config")
@Slf4j
public record PageConfig(
        int defaultPageNum,
        int defaultPageSize
) {
    @PostConstruct
    void print() {
        log.info("page-props = {} ", this);
    }
}
