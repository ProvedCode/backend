package com.provedcode.config;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "page-config")
@Slf4j
public record PageProperties(
        int defaultPageNum,
        int defaultPageSize
) {
    @PostConstruct
    void print() {
        log.info("page-props = {} ", this);
    }
}
