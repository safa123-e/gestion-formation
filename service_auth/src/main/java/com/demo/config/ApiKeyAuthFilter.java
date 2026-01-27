package com.demo.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

	@Value("${security.api.key}")
	private String expectedApiKey;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String contextPath = request.getContextPath(); 

		String path = request.getRequestURI();
		if (path.startsWith(contextPath + "/auth/signin")) {
			String apiKey = request.getHeader("x-api-key");
			if (!expectedApiKey.equals(apiKey)) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Invalid or missing API Key");
				return;
			}
		}

		filterChain.doFilter(request, response);
	}
}