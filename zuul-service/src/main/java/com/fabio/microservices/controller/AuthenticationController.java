package com.fabio.microservices.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fabio.microservices.mapper.JwtMapper;
import com.fabio.microservices.properties.JwtProperties;
import com.fabio.microservices.response.JsonResponse;
import com.fabio.microservices.security.config.UserDetailsCustom;
import com.fabio.microservices.security.dto.JwtUser;
import com.fabio.microservices.security.service.JwtService;
import com.fabio.microservices.security.service.UserDetailsCustomService;

@RestController
@RequestMapping(value="/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsCustomService userDetailsCustomService;
	
	private static final Logger log=LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private JwtProperties jwtProperties;
	

	

	
	/**Servizio esposto dall'api gateway che autentica l'utente e rilascia un token di autorizzazione generato da un servizio esterno*/
	@PostMapping("")
	public JsonResponse authentication(@RequestParam(name="username")String username,@RequestParam(name="password")String password,HttpServletResponse response) {
		Authentication auth=null;
		UserDetailsCustom userDetails=null;
		JsonResponse jsonResponse=new JsonResponse();
		JwtUser jwtUser=null;
		try {
			if(username!=null && password!=null) {
				auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
				SecurityContextHolder.getContext().setAuthentication(auth);
				 if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
						userDetails=(UserDetailsCustom)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
						jwtUser=JwtMapper.getJwtUser(userDetails,jwtProperties.getSecret(),jwtProperties.getExpiration());
						jwtUser.setUsername(username);
						jwtUser=jwtService.generaToken(jwtUser);
						if(jwtUser.getToken()!=null) {
							response.setHeader(jwtProperties.getToken(),jwtUser.getToken());
							response.setHeader("username",userDetails.getUsername());
							jsonResponse.setSuccesso(true);
							jsonResponse.setMessaggio("Token correttamente generato");
							jsonResponse.setResponse(jwtUser);
						}else {
							jsonResponse.setMessaggio("Non è stato possibile generare il token di autorizzazione");
							jsonResponse.setSuccesso(false);
						}
					}
				}
		}catch(Exception e) {
			log.error(e.getMessage(),e);
			jsonResponse.setMessaggio(e.getMessage());
		}
		return jsonResponse;
	}
}
