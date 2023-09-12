package com.cosmo_bank.restapi.config;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	@Value("${jwt.token.validity}")
	public long TOKEN_VALIDITY;

	@Value("${jwt.signing.key}")
	public String SIGNING_KEY;
	
	public String generateToken(Authentication authentication) {
		
		// User object here is org.springframework.security.core.userdetails.User
		// not the one of the entities
		User userPrincipal = (User) authentication.getPrincipal();
		
		return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date())
			.setExpiration(new Date(new Date().getTime() + TOKEN_VALIDITY))
			.signWith(key(), SignatureAlgorithm.HS256).compact();
	}
	
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SIGNING_KEY));
	}
	
	public String getUsernameFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJwt(token).getBody().getSubject();
	}
	
	public boolean validateJwtToken(String token) {
		
		try {
			Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
			return true;
		} catch (MalformedJwtException e) {
			System.out.println("Invalid JWT token: " + e.getMessage());
		} catch (ExpiredJwtException e) {
			System.out.println("JWT token is expired: " + e.getMessage());
		} catch (UnsupportedJwtException e) {
			System.out.println("JWT token is unsupported: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println("JWT claims string is empty: " + e.getMessage());
		}

		return false;
	}

}
