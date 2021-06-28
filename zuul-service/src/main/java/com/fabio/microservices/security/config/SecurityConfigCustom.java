package com.fabio.microservices.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fabio.microservices.exception.JwtAuthException;
import com.fabio.microservices.security.enumeration.PathPermissionEnumeration;
import com.fabio.microservices.security.filtri.JwtAutenticazioneFilter;
import com.fabio.microservices.security.service.UserDetailsCustomService;
@Configuration
@EnableWebSecurity
public class SecurityConfigCustom extends WebSecurityConfigurerAdapter{
	
	private static final Logger log=LoggerFactory.getLogger(SecurityConfigCustom.class);
	
	@Autowired
	private JwtAuthException jwtAuthException;
	
	@Autowired
	private UserDetailsCustomService userDetailsCustomService;
	//utilizziamo la codifica BCrypr per le password, che saranno salvate o controllate secondo questa codifica 
	//sul nostro db interno da spring
	@Bean
	public PasswordEncoder getPasswordEncorder()
	{
		return new BCryptPasswordEncoder();
	}	
	@Bean
	public JwtAutenticazioneFilter getJwtAutenticazioneFilter() {
		return new JwtAutenticazioneFilter();
	}
	
	//iniettiamo nel contesto l'oggetto necessario per l'autenticazione
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder auth) {
		try {
			auth.userDetailsService(userDetailsCustomService).passwordEncoder(getPasswordEncorder());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void configure(HttpSecurity http) {
		try {
			http.cors().and().csrf().disable().exceptionHandling()
			.authenticationEntryPoint(this.jwtAuthException)
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			http.authorizeRequests().antMatchers("/auth").permitAll()
			.antMatchers("/services/rs/api/apl/**").hasAnyAuthority("apl","amministratore")
			.antMatchers("/services/rs/api/admin/**").hasAuthority("amministratore")
			.antMatchers("/services/rs/api/fondo/**").hasAnyAuthority("fondo","amministratore").anyRequest().authenticated();
			
			//prima dei filtri di spring, controlliamo che non sia già presente un token valido
			http.addFilterBefore(getJwtAutenticazioneFilter(), UsernamePasswordAuthenticationFilter.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		
	}
}
