package com.fabio.microservices.security.filtri;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fabio.microservices.properties.JwtProperties;
import com.fabio.microservices.security.config.UserDetailsCustom;
import com.fabio.microservices.security.dto.JwtUser;
import com.fabio.microservices.security.service.JwtService;
import com.fabio.microservices.security.service.UserDetailsCustomService;


public class JwtAutenticazioneFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtProperties jwtProperties;
	
	@Autowired
	private UserDetailsCustomService userDetailsServices;
	
	@Autowired
	private JwtService jwtService;
	
	private static final Logger log=LoggerFactory.getLogger(JwtAutenticazioneFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token=null;
		boolean validato=false;
		boolean scaduto=false;
		JwtUser user=null;
		boolean redirect=false;
		UserDetailsCustom userDetails=null;
		if(request.getHeader(jwtProperties.getToken())!=null && request.getHeader("username")!=null) {
			token=request.getHeader(jwtProperties.getToken());
			if(token!=null) {
				user=new JwtUser();
				user.setToken(token);
				user.setExpiration(jwtProperties.getExpiration());
				user.setSecret(jwtProperties.getSecret());
				user.setUsername(request.getHeader("username"));
				validato=jwtService.validaToken(user);
				//verifichiamone la firma  e confrontiamo l'username dell'header
				if(validato) {
					//verifichiamone la scadenza
					scaduto=jwtService.scaduto(user);
					if(scaduto) {
						//rigeneriamo il token
						try {
							user=jwtService.refresh(user);
						} catch (Exception e) {
							log.error(e.getMessage(),e);
						}
						}
						try {
							//recuperiamo username e dati ruoli dal token
							
							if(SecurityContextHolder.getContext()!=null && SecurityContextHolder.getContext().getAuthentication()!=null && SecurityContextHolder.getContext().getAuthentication().getPrincipal()==null ) {
								userDetails=(UserDetailsCustom)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
							}else {
								userDetails=(UserDetailsCustom)userDetailsServices.loadUserByUsername(request.getHeader("username"));
								UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
								authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
								SecurityContextHolder.getContext().setAuthentication(authentication);
							}
							
							response.setHeader(jwtProperties.getToken(), user.getToken());
							response.setHeader("username",request.getHeader("username"));
							response.setStatus(HttpStatus.SC_ACCEPTED);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							log.error(e.getMessage(),e);
							redirect=true;
						}
					
					
				}else {
					response.setStatus(HttpStatus.SC_UNAUTHORIZED);
					redirect=true;
				}
			}else {
				redirect=true;
			}
		}else {
			redirect=true;
		}
		
			filterChain.doFilter(request, response);
		
	}
	
	
	
}
