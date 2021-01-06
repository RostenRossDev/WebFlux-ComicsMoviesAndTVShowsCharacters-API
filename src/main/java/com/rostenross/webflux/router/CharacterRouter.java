package com.rostenross.webflux.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.rostenross.webflux.handler.CharacterHandler;


@Configuration
public class CharacterRouter {
	
	@Bean
	public RouterFunction<ServerResponse> characterRoute(CharacterHandler handler){
		return 	RouterFunctions
					.route(RequestPredicates.GET("/character/all").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::findAll)
					.andRoute(RequestPredicates.GET("/character/id:{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::findById)
					.andRoute(RequestPredicates.POST("/character").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::save)
					.andRoute(RequestPredicates.DELETE("/character/delete/all").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::deleteAll);
	}
}
