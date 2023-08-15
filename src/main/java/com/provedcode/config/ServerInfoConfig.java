package com.provedcode.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.server.ResponseStatusException;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;

@AllArgsConstructor
@Configuration
public class ServerInfoConfig {
    private Environment env;

    public String getServerPort() {
        return env.getProperty("server.port");
    }
    public String getIpAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new ResponseStatusException(NOT_IMPLEMENTED, "if you are system administrator: server doesn't know his ip (it's bad)");
        }
    }
    public String getFullServerAddress() {
        return getIpAddress() + ":" + getServerPort();
    }
}
