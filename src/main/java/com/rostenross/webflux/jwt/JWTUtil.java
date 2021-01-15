package com.rostenross.webflux.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rostenross.webflux.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	private final Logger LOG = LoggerFactory.getLogger(JWTUtil.class);
	
	private String secret = "RostenRossDev_29_CostantiniNestorMatias_16041991";
	
	private String expireTime = "30000";
	
	public String generateToken(User user) {
		
		Date now = new Date();
		Map<String, Object> claim = new HashMap<>();
		
		claim.put("alg", "HS256");
		claim.put("typ", "JWT");
		
		return Jwts.builder()
				.setSubject(user.getUsername())
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime()+Long.parseLong(expireTime)*1000))
				.signWith(SignatureAlgorithm.HS256,Base64.getEncoder().encodeToString(secret.getBytes()))
				.setHeaderParams(claim)
				.compact();
	}
	
	public Claims getClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes()))
				.parseClaimsJws(token)
				.getBody();
	}
	
	public String getUsernameFromToken(String token) {
		return getClaimsFromToken(token).getSubject();
	}
	
	public Date getExpirationDte(String token) {
		return getClaimsFromToken(token).getExpiration();
	}
	
	public Boolean isTokenExpired (String token) {
		Date expirationDate = getExpirationDte(token);
		return expirationDate.before(new Date());
	}
	
	public Boolean isTokenVlidated(String token) {
		return !isTokenExpired(token);
	}
	
}
