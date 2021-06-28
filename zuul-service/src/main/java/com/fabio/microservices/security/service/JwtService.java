package com.fabio.microservices.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fabio.microservices.feign.proxy.JwtTokenServiceProxy;
import com.fabio.microservices.security.dto.JwtUser;
import com.fabio.microservices.security.repository.UtenteRepository;

@Service
public class JwtService {
	
	@Autowired
	private JwtTokenServiceProxy jwtProxy;
	private Logger log=LoggerFactory.getLogger(JwtService.class);
	
	public JwtService() {
		
	}
	
	
	public JwtUser generaToken(JwtUser user) {
		try {
			return jwtProxy.generaToken(user);
		}catch(Exception e) {
			log.error(e.getMessage(),e);
		}
		return user;
	}
	
	public boolean validaToken(JwtUser user) {
		try {
			return jwtProxy.validaToken(user);
		}catch(Exception e) {
			log.error(e.getMessage(),e);
		}
		return false;
	}
	
	public JwtUser refresh(JwtUser user) throws Exception{
		try {
			return jwtProxy.refreshToken(user);
		}catch(Exception e) {
			log.error(e.getMessage(),e);
		}
		throw new Exception("Si è verificato un errore");
	}
	
	public boolean scaduto(JwtUser user) {
		try {
			return jwtProxy.isScaduto(user);
		}catch(Exception e) {
			log.error(e.getMessage(),e);
		}
		return false;
	}
	public JwtUser getUset(JwtUser user) throws Exception{
		try {
			user=jwtProxy.getUser(user);
		}catch(Exception e) {
			log.error(e.getMessage(),e);
		}
		throw new Exception("Si è verificato un errore");
	}
}
