package com.rostenross.webflux.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rostenross.webflux.model.Character;
import com.rostenross.webflux.service.ServiceCharacterImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@RestController
//@RequestMapping("/character")
public class CharactersController {
	/*
	@Autowired
	private ServiceCharacterImpl repo;
	
	@GetMapping
	public Flux<Character> all(){
		return repo.getAll();
	}
	
	@GetMapping("/id:{id}")
	public Mono<Character> byId(@PathVariable("id") Long id){
		return repo.getById(id);
	}
	
@PostMapping
	public Mono<Character> add(@RequestBody Character character){
		return repo.create(character);
	}
	*/
}


