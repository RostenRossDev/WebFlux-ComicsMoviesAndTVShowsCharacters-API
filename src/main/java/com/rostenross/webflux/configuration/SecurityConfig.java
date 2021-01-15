package com.rostenross.webflux.configuration;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.ServerWebExchange;

import com.rostenross.webflux.jwt.AuthenticationManager;
import com.rostenross.webflux.jwt.SecurityContextRepsitory;
import com.rostenross.webflux.repository.UserRepository;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	private final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private SecurityContextRepsitory securityContext;
	
	
	@Bean
	ReactiveUserDetailsService userDdetailService() {
		return (name) -> repo.findByUsername(name);
	}
	
	
	
	@Bean
	public SecurityWebFilterChain securityWebFilterChain (ServerHttpSecurity http) {
		return http.authorizeExchange(
				authorizedExchangeSpec -> authorizedExchangeSpec
					.pathMatchers("/api/v1/character/singin","/api/v1/character/login", "/api/v1/character/about","/api/v1/character/all","/api/v1/character/id=**","/api/v1/character/name=**")
						.permitAll()
					.anyExchange().authenticated()

		)
				.exceptionHandling()
				.authenticationEntryPoint((response, error) -> Mono.fromRunnable(()->{
					response.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				})).accessDeniedHandler((response,error) -> Mono.fromRunnable(()->{
					response.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
				}))
				.and()
                .httpBasic().disable()
				.formLogin().disable()
				.logout().disable()
				.csrf().disable()
				.authenticationManager(authManager)
				.securityContextRepository(securityContext)
				.requestCache().requestCache(NoOpServerRequestCache.getInstance())
				.and()
				.build();
				
				
				
				/*.and()
				.httpBasic().disable()
				.formLogin().disable()
				.csrf().disable()
				.authenticationManager(authenticationManager)
				.securityContextRepository(securityContextRepository)
				.requestCache().requestCache(NoOpServerRequestCache.getInstance())
				.and()
				.build();*/
	
		
		
		
				/*.pathMatchers("/api/v1/character/singin","/api/v1/character/login", "/api/v1/character/about","/api/v1/character/all","/api/v1/character/id=**","/api/v1/character/name=**").permitAll()
				.pathMatchers("/admin").hasAnyAuthority("ROLE_ADMIN")
				.anyExchange().authenticated()
				.and()
				.httpBasic().disable()
				.formLogin().disable()
					//.loginPage("/login")
				//.and()
				.logout()
					//.logoutUrl("/logout")
				.and()
					.csrf( ).disable()
				.authenticationManager(authenticationManager)
				.securityContextRepository(securityContextRepository)
				.build();
				*/
	}
	
	
	/*@Bean
	public MapReactiveUserDetailsService userDetailsService() {
		String username="RostenRossAdmin_";
		String password="35689682Kalifa_";
		String role="ADMIN";
		UserDetails userDetails = User.withUsername(username)
				.password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password))
				.roles(role)
				.build();
		return new MapReactiveUserDetailsService(userDetails);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/
	
}
