package com.provedcode;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
@OpenAPIDefinition
public class ProvedCodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProvedCodeApplication.class, args);
	}

}
