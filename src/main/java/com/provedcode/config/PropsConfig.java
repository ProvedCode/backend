package com.provedcode.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@EnableConfigurationProperties(PageProperties.class)
@PropertySource("pagination.properties")
@Configuration
public class PropsConfig {
}
