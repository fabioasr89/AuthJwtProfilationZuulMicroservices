package com.fabio.microservices.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.fabio.microservices.properties.JwtProperties;

@SpringBootApplication
@EnableZuulProxy
@ComponentScan(basePackages= {"com.fabio.microservices.security.service","com.fabio.microservices.controller","com.fabio.microservices.security.config","com.fabio.microservices.exception"})
@EnableJpaRepositories(basePackages= {"com.fabio.microservices.security.repository"})
@EntityScan(basePackages= {"com.fabio.microservices.security.model"})
@EnableFeignClients(basePackages= {"com.fabio.microservices.feign.proxy"})
@EnableConfigurationProperties(value = JwtProperties.class)
public class ZuulApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run( ZuulApplication.class, args);

	}

}
