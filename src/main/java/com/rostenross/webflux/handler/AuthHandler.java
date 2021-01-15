package com.rostenross.webflux.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.rostenross.webflux.jwt.JWTUtil;
import com.rostenross.webflux.dto.AuthResponse;
import com.rostenross.webflux.model.Character;
import com.rostenross.webflux.model.User;
import com.rostenross.webflux.repository.UserRepository;

import reactor.core.publisher.Mono;

@Component
public class AuthHandler {

	private final Logger log = LoggerFactory.getLogger(AuthHandler.class);

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private UserRepository repo;
	
	public Mono<ServerResponse> getToken(ServerRequest req){
		Mono<User> userMono=req.bodyToMono(User.class);
		
		return userMono
				.flatMap(user -> repo.findByUsername(user.getUsername())
						.flatMap(userDetails -> {
							if(user.getPassword().equals(userDetails.getPassword())) {
								AuthResponse auth =new AuthResponse(jwtUtil.generateToken(user));
								log.info(auth.getJwt());
								return ServerResponse.ok().bodyValue(auth);
							}else {
								return ServerResponse.badRequest().build();
							}
						}).switchIfEmpty(ServerResponse.notFound().build())
				);
	}
	
	public Mono<ServerResponse> generateUser(ServerRequest req){
		
		Mono<User> newUser = req.bodyToMono(User.class);
		return newUser
				.map(mapUser -> {
					mapUser.setEncodePassword(mapUser.getPassword());
					log.info("password: "+mapUser.getPassword());
					log.info("user: "+mapUser.getUsername());
					
					return mapUser;
				})
				.flatMap(user -> repo.save(user))
				.flatMap(userDetail ->{
					return ServerResponse.ok().bodyValue(Mono.just(userDetail));
				});
	}
	
	public Mono<ServerResponse> getAll(ServerRequest req){
		return  ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(repo.findAll(), Character.class);
				//ServerResponse.ok().bodyValue(Mono.empty());
	}
}