package com.provedcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ProvedCodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProvedCodeApplication.class, args);
	}

}
