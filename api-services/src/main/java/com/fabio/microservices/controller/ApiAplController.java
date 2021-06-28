package com.fabio.microservices.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping(value="/apl/")
public class ApiAplController {
	
	
		@PostMapping("saluto")
		public String helloApl() {
			return "Ciao Apl";
		}
		
		
		
	
}
