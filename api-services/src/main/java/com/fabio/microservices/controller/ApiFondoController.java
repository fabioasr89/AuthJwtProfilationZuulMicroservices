package com.fabio.microservices.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping(value="/fondo/")
public class ApiFondoController {
	
	@PostMapping("saluto")
	public String helloFondo() {
		return "Ciao fondo";
	}
	
}
