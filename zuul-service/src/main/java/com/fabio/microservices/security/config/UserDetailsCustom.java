package com.fabio.microservices.security.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fabio.microservices.security.model.AuthUser;

public class UserDetailsCustom implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AuthUser utente;
	
	private static final Logger log=LoggerFactory.getLogger(UserDetailsCustom.class);
	public UserDetailsCustom(AuthUser utente) {
		this.utente=utente;
	}
	
	//profiliamo l'utenza in funzione del ruolo
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> autorizzazioni=null;
		try {
			autorizzazioni=new ArrayList<GrantedAuthority>();
			if(utente.getRuolo()!=null) {
				autorizzazioni.add(new SimpleGrantedAuthority(utente.getRuolo().getNome()));
			}
		}catch(Exception e) {
			log.error(e.getMessage(),e);
		}
		return autorizzazioni;
	}

	@Override
	public String getPassword() {
		return utente.getPassword();
	}

	@Override
	public String getUsername() {
		return utente.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

}
