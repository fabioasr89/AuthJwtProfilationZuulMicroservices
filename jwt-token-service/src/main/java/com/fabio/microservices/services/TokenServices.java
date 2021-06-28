package com.fabio.microservices.services;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabio.microservices.dto.JwtUser;
import com.fabio.microservices.jwt.JwtAuthenticationUtil;

import io.jsonwebtoken.Claims;

@Service
public class TokenServices implements TokenServicesInterface{
	
	private static final Logger log=LoggerFactory.getLogger(TokenServices.class);
	@Autowired
	private JwtAuthenticationUtil jwtAuthUtil;
	

	@Override
	public JwtUser generaToken(JwtUser user) {
		
		return jwtAuthUtil.generaToken(user);
	}

	



	@Override
	public boolean scaduto(JwtUser user) {
		
		return jwtAuthUtil.scaduto(user);
	}

	@Override
	public boolean validaToken(JwtUser user) {
		
		return jwtAuthUtil.validaToken(user);
	}



	@Override
	public JwtUser getUserDaToken(JwtUser user) throws Exception{
		
		return jwtAuthUtil.getJwtUserDaToken(user);
	}





	@Override
	public JwtUser refreshToken(JwtUser user) {
		
		return jwtAuthUtil.refreshToken(user);
	}
	
	
}
