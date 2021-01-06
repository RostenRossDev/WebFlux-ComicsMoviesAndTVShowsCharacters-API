package com.rostenross.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.rostenross.webflux.model.Character;

import reactor.core.publisher.Mono;

@Repository
public interface ICharacterRepository extends ReactiveMongoRepository<Character, String>{

	public Mono<Character> findByFullName(String name);
}
