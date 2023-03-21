package com.provedcode.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "page-config")
public record PageConfig(
        int defaultPageNum,
        int defaultPageSize
) {}
