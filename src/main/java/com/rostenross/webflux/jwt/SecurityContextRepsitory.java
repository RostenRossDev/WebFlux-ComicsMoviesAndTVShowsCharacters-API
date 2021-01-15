package com.rostenross.webflux.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class SecurityContextRepsitory implements ServerSecurityContextRepository{

	private AuthenticationManager manager;
	
	public SecurityContextRepsitory(AuthenticationManager manager) {
		this.manager=manager;
	}
	
	@Override
	public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
		// TODO Auto-generated method stub
		return Mono.empty();
	}

	@Override
	public Mono<SecurityContext> load(ServerWebExchange exchange) {
		// TODO Auto-generated method stub
		String bearer = "Bearer ";
		return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
				.filter(b -> b.startsWith(bearer))
				.map(subs -> subs.substring(bearer.length()))
				.flatMap(token -> Mono.just(new UsernamePasswordAuthenticationToken(token, token, java.util.Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")))))
				.flatMap(auth -> manager.authenticate(auth).map(SecurityContextImpl::new));
	}

	
}
