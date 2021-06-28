package com.fabio.microservices.security.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabio.microservices.exception.AuthException;
import com.fabio.microservices.security.model.AuthUser;
import com.fabio.microservices.security.repository.UtenteRepository;

@Service
public class AuthService {
	
	private static final Logger log=LoggerFactory.getLogger(AuthService.class);
	
	@Autowired
	public UtenteRepository utenteRepository;
	
	public AuthUser findByIdUtente(String username) {
		AuthUser auth=null;
		Optional<AuthUser> optional=null;
		try {
			optional=utenteRepository.findById(username);
			if(optional.isPresent()) {
				return auth=optional.get();
			}else {
				throw new AuthException("Nessun utente censito con il seguente username:"+username);
			}
		}catch(Exception e) {
			log.error(e.getMessage(),e);
		}
		return auth;
	}
}
