package com.ragnar.MySchoolManagement.auth;

import static com.ragnar.MySchoolManagement.constanst.SecurityConstants.OPTIONS_HTTP_METHOD;
import static com.ragnar.MySchoolManagement.constanst.SecurityConstants.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.ragnar.MySchoolManagement.user.User;
import com.ragnar.MySchoolManagement.user.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {


	private final UserRepository userService;

	private final TokenProvider tokenProvider;

	public AuthorizationFilter(TokenProvider tokenProvider,UserRepository userService) {
		this.tokenProvider = tokenProvider;
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// get the http method
		if (request.getMethod().equalsIgnoreCase(OPTIONS_HTTP_METHOD)) {

			response.setStatus(HttpStatus.OK.value());
		} else {
			// get the header from the request
			String authorizationHeader = request.getHeader(AUTHORIZATION);

			// ensure the header starts with "bearer"
			if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {

				filterChain.doFilter(request, response);

				return;
			}

			try {
				// get the token
				String token = authorizationHeader.substring(TOKEN_PREFIX.length());
				// get the username from the token
				String username = tokenProvider.getSubject(token);

				Optional<User> loggedInUser = userService.findByEmail(username);

				// ensure user is in database
				if (loggedInUser != null) {

					// ensure token is valid & the user doesn't have a security context...
					if (tokenProvider.isTokenValid(username, token)
							&& SecurityContextHolder.getContext().getAuthentication() == null) {

						// get the authorities for the user
						List<GrantedAuthority> authorities = tokenProvider.getAuthorities(token);

						// authenticate the token and the user
						Authentication authentication = tokenProvider.getAuthentication(username, authorities, request);

						// set the authentication
						SecurityContextHolder.getContext().setAuthentication(authentication);

					}
				} else {
					SecurityContextHolder.clearContext();

				}
				filterChain.doFilter(request, response);

			} catch (TokenExpiredException e) {
				e.getMessage();

			}
		}
	}
}
