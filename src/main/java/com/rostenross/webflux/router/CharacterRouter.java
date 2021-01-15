package com.rostenross.webflux.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.rostenross.webflux.handler.AuthHandler;
import com.rostenross.webflux.handler.CharacterHandler;
import com.rostenross.webflux.repository.UserRepository;


@Configuration
public class CharacterRouter {
	
	@Autowired
	private CharacterHandler characterHandler;
	
	@Autowired
	private AuthHandler authHandler;
	
		
	@Bean
	public RouterFunction<ServerResponse> characterRoute(){
		return 	RouterFunctions
					.route(RequestPredicates.GET("/api/v1/character/all").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), characterHandler::findAll)
					.andRoute(RequestPredicates.GET("/api/v1/character/id={id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), characterHandler::findById)
					.andRoute(RequestPredicates.POST("/api/v1/character").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), characterHandler::save)
					.andRoute(RequestPredicates.POST("/api/v1/character/many").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), characterHandler::saveAll)
					.andRoute(RequestPredicates.DELETE("/api/v1/character/delete/all").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), characterHandler::deleteAll)
					.andRoute(RequestPredicates.GET("/api/v1/character/name={name}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), characterHandler::findByName)
					.andRoute(RequestPredicates.DELETE("/api/v1/character/delete/id={id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), characterHandler::delete)
					.andRoute(RequestPredicates.POST("/api/v1/character/login").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), authHandler::getToken)
					.andRoute(RequestPredicates.POST("/api/v1/character/singin").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), authHandler::generateUser)
					.andRoute(RequestPredicates.GET("/api/v1/character/users").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), authHandler::getAll);
	}
	
}
