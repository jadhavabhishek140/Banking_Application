package com.cosmo_bank.restapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.annotation.Resource;
import jakarta.servlet.Filter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Resource(name="userService")
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UnauthorizedEntryPoint unauthorizedEntryPoint;
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		
		authProvider.setUserDetailsService(userDetailsService);
		
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	public Filter authTokenFilter() {
		return new JwtRequestFilter();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.csrf(csrf -> csrf.disable())
			.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedEntryPoint))
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(
					auth -> auth.requestMatchers("/auth/**", "/add/**").permitAll()
								.requestMatchers("/cust/**").hasAnyRole("CUSTOMER")
								.requestMatchers("/empl/**").hasAnyRole("EMPLOYEE")
								.requestMatchers("/admin/**").hasAnyRole("ADMIN")
								.anyRequest().authenticated()
			);
		
		http.authenticationProvider(authenticationProvider());
		
		http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
}
