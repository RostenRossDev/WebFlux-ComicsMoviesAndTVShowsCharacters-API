package com.rostenross.webflux.controllers;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;



public final class LoginController {

	private final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping("/")
	public Mono<String> greet(Mono<Principal> principal){
		return principal
				.map(Principal::getName)
				.map(name -> String.format("Hello, %s", name));
	}
	
	@GetMapping("/admin")
	public Mono<String> greeAdmin(Mono<Principal> principal){
		return principal
				.map(Principal::getName)
				.map(name -> String.format("Adim acces: %s", name));
	}
	
	
	@GetMapping("/about")
	public ResponseEntity< Mono<String>> about(){
		log.info("hola");
		return new ResponseEntity<>( Mono.just("Hola "), HttpStatus.OK);
	}
}

