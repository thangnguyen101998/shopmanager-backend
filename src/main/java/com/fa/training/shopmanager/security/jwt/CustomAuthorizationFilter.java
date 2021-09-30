package com.fa.training.shopmanager.security.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getServletPath().equals("/login") || request.getServletPath().equals("/token/refresh/**")) {
			filterChain.doFilter(request, response);
		} else {
			String authorizationHeader = request.getHeader("Authorization");
			if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				String token = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(token);
				String username = decodedJWT.getSubject();
				String[] roles = decodedJWT.getClaim("role").asArray(String.class);
				Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
				Arrays.stream(roles).forEach(role -> {
					authorities.add(new SimpleGrantedAuthority(role));
				});
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null, authorities);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				filterChain.doFilter(request, response);
 			}else {
 				filterChain.doFilter(request, response);
 			}
		}
	}

}
