package com.fabio.microservices.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages= {"com.fabio.microservices.services","com.fabio.microservices.controller","com.fabio.microservices.jwt"})
public class TokenServicesApplication {
	private static final Logger log=LoggerFactory.getLogger(TokenServicesApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(TokenServicesApplication.class, args);
		log.debug("TOKEN SERVICES CORRETTAMENTE AVVIATO");
	}

}
