package com.fa.training.shopmanager.security.jwt;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fa.training.shopmanager.dto.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authenticationManager;
	
	public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			LoginDTO loginDTO = mapAuthenticationRequest(request);
			String username = loginDTO.getUsername();
			String password = loginDTO.getPassword();
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
			return authenticationManager.authenticate(authenticationToken);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
	 private LoginDTO mapAuthenticationRequest(HttpServletRequest request) throws IOException {
	        return new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);
	    }

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		User user = (User)authResult.getPrincipal();
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
		String access_token = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 120 * 60 * 1000))
				.withIssuer(request.getRequestURI().toString())
				.withClaim("role", user.getAuthorities().stream().map(GrantedAuthority :: getAuthority).collect(Collectors.toList()))
				.sign(algorithm);
		
		  String refresh_token = JWT.create()
	                .withSubject(user.getUsername())
	                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
	                .withIssuer(request.getRequestURL().toString())
	                .withClaim("role", user.getAuthorities().stream().map(GrantedAuthority :: getAuthority).collect(Collectors.toList()))
	                .sign(algorithm);
		 
	        Cookie refreshCookie = new Cookie("RefreshToken", refresh_token);
	        refreshCookie.setMaxAge(10*24*60*60);
	        refreshCookie.setHttpOnly(true);

	        response.addCookie(refreshCookie);
	        response.setHeader("Set-Cookie", response.getHeader("Set-Cookie") + "; SameSite=strict");

		  
	        Map<String, String> tokens = new HashMap<>();
	        tokens.put("access_token", access_token);
	        response.setContentType("application/json");
	        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
	}
	
	
}
