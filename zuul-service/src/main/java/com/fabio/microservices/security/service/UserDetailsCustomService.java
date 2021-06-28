package com.fabio.microservices.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fabio.microservices.security.config.UserDetailsCustom;
import com.fabio.microservices.security.model.AuthUser;
import com.fabio.microservices.security.repository.UtenteRepository;

@Service
public class UserDetailsCustomService implements org.springframework.security.core.userdetails.UserDetailsService{
	@Autowired
	private UtenteRepository utenteRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AuthUser user=null;
		Optional<AuthUser> optionalUser=null;
		if(username!=null) {
			optionalUser=utenteRepository.findById(username);
			if(optionalUser.isPresent()) {
				user=optionalUser.get();
				return new UserDetailsCustom(user);
			}else {
				throw new UsernameNotFoundException("Nessun utente associato a questo username. Impossibile recuperare l'utenza");
			}
		}else {
			throw new UsernameNotFoundException("Username non presente. Impossibile recuperare l'utenza");
		}
	}
	
	
	
}
