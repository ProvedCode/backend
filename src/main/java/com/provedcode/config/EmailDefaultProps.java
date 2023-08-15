package com.provedcode.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "default-email")
@Slf4j
public record EmailDefaultProps(
        String userDeleted,
        String userDeletedSubject
) {
}
