package com.rostenross.webflux.service;

import com.rostenross.webflux.model.Character;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICharacterService {

	public Flux<Character> getAll();
	
	public Mono<Character> getById(String id);
	
	public Flux<Character> getByName(String name);
	
	public Mono<Character> create(Character caracter);
	
	public Flux<Character> createMany(Flux<Character> character);
	
	public Mono<Character> update(Character character, String id);
	
	public Mono<Character> delete(String id);
	
	public Mono<Void> deleteAll();
}
