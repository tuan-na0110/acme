package com.acme;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.acme.model.ResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The filter class for each request
 */
@Component
public class RequestFilter extends OncePerRequestFilter {

	@Autowired
	private ObjectMapper mapper;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {

		// check if request has a valid token in header
		String token = httpServletRequest.getHeader("Token");
		if (token == null || !token.equals("simpleToken")) {
			httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
			httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
			mapper.writeValue(httpServletResponse.getWriter(),
					new ResponseModel(HttpStatus.UNAUTHORIZED.value(), "invalid token"));
		} else {
			filterChain.doFilter(httpServletRequest, httpServletResponse);
		}
	}
}
