package com.fabio.microservices.mapper;

import org.springframework.security.core.GrantedAuthority;

import com.fabio.microservices.security.config.UserDetailsCustom;
import com.fabio.microservices.security.dto.JwtUser;

public class JwtMapper {
	
	
	
	public static JwtUser getJwtUser(UserDetailsCustom userDetails,String secret,Long expiration) {
		JwtUser jwtUser=new JwtUser();
		for(GrantedAuthority authorities: userDetails.getAuthorities()) {
			jwtUser.getRuoli().add(authorities.getAuthority());
		}
		jwtUser.setUsername(userDetails.getUsername());
		jwtUser.setSecret(secret);
		jwtUser.setExpiration(expiration);
		return jwtUser;
	}
}
