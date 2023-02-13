package com.ragnar.MySchoolManagement.auth;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ragnar.MySchoolManagement.constanst.SecurityConstants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {

	// if user is not authenticated this class gets triggered 

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
			throws IOException {

		// send error response
		HttpResponse myResponse = new HttpResponse(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN,
				HttpStatus.FORBIDDEN.getReasonPhrase(), SecurityConstants.FORBIDDEN_MESSAGE);

		response.setContentType(APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.FORBIDDEN.value());

		// output stream to send response
		OutputStream outputStream = response.getOutputStream();

		// object mapper to send response back in json
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(outputStream, myResponse);

		outputStream.flush();
	}

}
