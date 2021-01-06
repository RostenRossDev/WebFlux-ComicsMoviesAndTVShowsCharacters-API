package com.rostenross.webflux.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.rostenross.webflux.model.Character;
import com.rostenross.webflux.service.ServiceCharacterImpl;

import jdk.jfr.ContentType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CharacterHandler {
	private final Logger log = LoggerFactory.getLogger(CharacterHandler.class);
	@Autowired
	private ServiceCharacterImpl service;
	
	public Flux<ServerResponse> findAll(ServerRequest req) {
		log.info(service.getAll().toString());
		log.info("En el handler de findall");
		Flux<Character> characters = service.getAll();
		return characters
				.flatMap(c -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(c, Character.class))
				.switchIfEmpty(ServerResponse.notFound().build());
		}
	
	
	public Mono<ServerResponse> findById(ServerRequest req){
		String id = req.pathVariable("id");
		return 	service.getById(id)
				.flatMap((c) -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(c, Character.class))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> save(ServerRequest req){
		Mono<Character> mono = req.bodyToMono(Character.class);
				
		return ServerResponse.ok()
			.contentType(MediaType.APPLICATION_JSON)
			.body(
				mono.flatMap(
					toWrite -> this.service.create(new Character(toWrite.getFullName(), toWrite.getDescription()))), Character.class);
	}
	public Mono<ServerResponse> delete(ServerRequest req){
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(service.delete(req.pathVariable("id")), Character.class);
	}
	
	public Mono<ServerResponse> deleteAll(ServerRequest req){
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(service.deleteAll(), Character.class);
	}
	
}
