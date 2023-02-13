package com.ragnar.MySchoolManagement.auth;

import static com.ragnar.MySchoolManagement.constanst.SecurityConstants.*;

import static java.util.Arrays.stream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.ragnar.MySchoolManagement.user.UserPrincipal;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class TokenProvider {

	@Value("${jwt.secret}")
	private String secret;

	public String generateToken(UserPrincipal userPrincipal) {
		// get the users authorities
		String[] claims = getClaimsfromUser(userPrincipal);
		// add the issuer
		return JWT.create().withIssuer(ISSUER)
				// who is using the token
				.withAudience(Egbedina_Inc_Administration)
				// date the token was issued
				.withIssuedAt(new Date())
				// a unique id for the user
				.withSubject(userPrincipal.getUsername())
				// users authorities
				.withArrayClaim(AUTHORITIES, claims)
				// when the token expires
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				
				// algorithm to encode the token
				.sign(Algorithm.HMAC512(secret.getBytes()));
	}

	private String[] getClaimsfromUser(UserPrincipal userPrincipal) {

		List<String> authorities = new ArrayList<>();

		for (GrantedAuthority grantedAuthority : userPrincipal.getAuthorities()) {
			// the getAuthority string
			authorities.add(grantedAuthority.getAuthority());
		}

		// return the authorities as an array
		return authorities.toArray(new String[0]);
	}

	// get authorities from the token
	public List<GrantedAuthority> getAuthorities(String token) {

		String[] claims = getClaimsFromToken(token);

		// convert claims to a list of SimpleGrantedAuthority
		return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

	}

	public Authentication getAuthentication(String username, List<GrantedAuthority> authorities,
			HttpServletRequest request) {

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null,
				authorities);

		authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		return authToken;

	}

	public boolean isTokenValid(String username, String token) {

		JWTVerifier verifier = getJWTVerifier();

		return StringUtils.isNotEmpty(username) && !isTokenExpired(verifier, token);

	}

	public String getSubject(String token) {

		JWTVerifier verifier = getJWTVerifier();

		return verifier.verify(token).getSubject();

	}

	private boolean isTokenExpired(JWTVerifier verifier, String token) {

		Date expired = verifier.verify(token).getExpiresAt();

		return expired.before(new Date());
	}

	private String[] getClaimsFromToken(String token) {

		JWTVerifier verifier = getJWTVerifier();

		return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
	}

	private JWTVerifier getJWTVerifier() {
		// define the verifier
		JWTVerifier verifier;

		try {
			// define the algorithm
			Algorithm algorithm = Algorithm.HMAC512(secret.getBytes());

			verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
		} catch (JWTVerificationException e) {
			throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
		}

		return verifier;
	}
}