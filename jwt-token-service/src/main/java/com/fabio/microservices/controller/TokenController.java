package com.fabio.microservices.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabio.microservices.dto.JwtUser;
import com.fabio.microservices.services.TokenServices;

@RestController
@RequestMapping(value="/services/token/")
public class TokenController {
	
	private static final Logger log=LoggerFactory.getLogger(TokenController.class);
	
	@Autowired
	private TokenServices tokenService;
	
	@PostMapping("genera")
	public JwtUser generaToken(@RequestBody JwtUser user) {
		try {
			return tokenService.generaToken(user);
		}catch(Exception e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}
	
	@PostMapping("parser")
	public JwtUser parser(@RequestBody JwtUser user) {
		try {
			return tokenService.getUserDaToken(user);
		}catch(Exception e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}
	
	@PostMapping("refresh")
	public JwtUser refreshToken(@RequestBody JwtUser user) {
		try {
			return tokenService.refreshToken(user);
		}catch(Exception e) {
			log.error(e.getMessage(),e);
		}
		return user;
	}
	
	@PostMapping("user")
	public JwtUser getUser(@RequestBody JwtUser user) {
		try {
			return tokenService.getUserDaToken(user);
		}catch(Exception e) {
			log.error(e.getMessage(),e);
		}
		return user;
	}
	
	
	@PostMapping("valida")
	public boolean validaToken(@RequestBody JwtUser user) {
		try {
			return tokenService.validaToken(user);
		}catch(Exception e) {
			log.error(e.getMessage(),e);
		}
		return false;
	}
	
	@PostMapping("isScaduto")
	public boolean isScaduto(@RequestBody JwtUser user) {
		try {
			return tokenService.scaduto(user);
		}catch(Exception e) {
			log.error(e.getMessage(),e);
		}
		return false;
	}
}
