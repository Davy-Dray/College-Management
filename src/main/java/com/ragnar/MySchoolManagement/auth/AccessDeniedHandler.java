package com.ragnar.MySchoolManagement.auth;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ragnar.MySchoolManagement.constanst.SecurityConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

		// send error response
		HttpResponse myResponse = new HttpResponse(HttpStatus.UNAUTHORIZED.value(), HttpStatus.FORBIDDEN,
				HttpStatus.UNAUTHORIZED.getReasonPhrase(), SecurityConstants.ACCESS_DENIED_MESSAGE);

		response.setContentType(APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());

		OutputStream outputStream = response.getOutputStream();

		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(outputStream, myResponse);

		outputStream.flush();

	}

}
