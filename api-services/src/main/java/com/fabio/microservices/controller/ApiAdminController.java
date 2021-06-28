package com.fabio.microservices.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/admin/")
public class ApiAdminController {

	@PostMapping("saluto")
	public String helloAdmin() {
		return "Ciao admin";
	}
	
	
	
}
