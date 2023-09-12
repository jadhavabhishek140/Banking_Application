package com.cosmo_bank.restapi.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cosmo_bank.restapi.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtRequestFilter extends OncePerRequestFilter {
	
    @Value("${jwt.header.string}")
    public String HEADER_STRING;
    
    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;
    
	@Autowired
	private JwtUtil jwtUtil;
	
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = parseJwt(request);
		
		if(token != null && jwtUtil.validateJwtToken(token)) {
			
			String username = jwtUtil.getUsernameFromToken(token);
			
			UserDetails userDetails = userService.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken authToken = 
					new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(authToken);
		}
		
		filterChain.doFilter(request, response);
		
	}

	private String parseJwt(HttpServletRequest request) {
		
		String header = request.getHeader(HEADER_STRING);
		
		if(StringUtils.hasText(header) && header.startsWith(TOKEN_PREFIX)) {
			return header.replace(TOKEN_PREFIX, "");
		}
		
		return null;
	}

}
