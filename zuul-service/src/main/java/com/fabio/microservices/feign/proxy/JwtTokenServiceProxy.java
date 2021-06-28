package com.fabio.microservices.feign.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fabio.microservices.security.dto.JwtUser;


@FeignClient("token-service")
public interface JwtTokenServiceProxy {
	
	@PostMapping("/services/token/genera")
	JwtUser generaToken(@RequestBody JwtUser user);
	
	@PostMapping("/services/token/valida")
	boolean validaToken(@RequestBody JwtUser user);
	
	@PostMapping("/services/token/isScaduto")
	boolean isScaduto(@RequestBody JwtUser user);
	
	@PostMapping("/services/token/parser")
	JwtUser parser(@RequestBody JwtUser user);
	
	@PostMapping("/services/token/refresh")
	JwtUser refreshToken(@RequestBody JwtUser user);
	
	@PostMapping("/services/token/user")
	public JwtUser getUser(@RequestBody JwtUser user);
}
