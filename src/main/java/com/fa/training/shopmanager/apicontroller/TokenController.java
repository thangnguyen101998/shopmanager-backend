package com.fa.training.shopmanager.apicontroller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fa.training.shopmanager.model.Role;
import com.fa.training.shopmanager.model.Users;
import com.fa.training.shopmanager.service.UserService;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class TokenController {

	@Autowired
	private UserService userService;

	@GetMapping("/token/refresh")
	public void refreshToken(@CookieValue("RefreshToken") String refreshCookie, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println(refreshCookie);
		String authorizationHeader = refreshCookie;
			try {
				String refresh_token = authorizationHeader;
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refresh_token);
				String username = decodedJWT.getSubject();
				Optional<Users> user = userService.findUserByEmailAnDeletedIsFalse(username);
				String access_token = JWT.create().withSubject(user.get().getEmail())
						.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
						.withIssuer(request.getRequestURL().toString()).withClaim("roles", Arrays
								.asList(user.get().getRole()).stream().map(Role::getName).collect(Collectors.toList()))
						.sign(algorithm);
				Map<String, String> tokens = new HashMap<>();
				tokens.put("access_token", access_token);
				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
	}

}
