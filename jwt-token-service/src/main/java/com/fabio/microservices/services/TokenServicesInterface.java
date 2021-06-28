package com.fabio.microservices.services;

import java.util.Date;
import java.util.Map;

import com.fabio.microservices.dto.JwtUser;

import io.jsonwebtoken.Claims;


public interface TokenServicesInterface {
	
	JwtUser generaToken(JwtUser user);
	
	JwtUser refreshToken(JwtUser user);
    
    boolean scaduto(JwtUser user) ;
    
    boolean validaToken(JwtUser user);
    
    JwtUser getUserDaToken(JwtUser user) throws Exception;
}
