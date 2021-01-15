package com.rostenross.webflux.jwt;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.rostenross.webflux.repository.UserRepository;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager{

	private JWTUtil jwtUtil;
	private UserRepository repo;
	
	
	
	public AuthenticationManager(JWTUtil jwtUtil, UserRepository repo) {
		super();
		this.jwtUtil = jwtUtil;
		this.repo = repo;
	}



	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		// TODO Auto-generated method stub
		String token = authentication.getCredentials().toString();
		String username= jwtUtil.getUsernameFromToken(token);
		
		return repo.findByUsername(username)
				.flatMap(userDetails ->{
					if(userDetails.getUsername().equals(username) && jwtUtil.isTokenVlidated(token)){
						return Mono.just(authentication);
					}else {
						return Mono.empty();
					}
				});
	}

}
