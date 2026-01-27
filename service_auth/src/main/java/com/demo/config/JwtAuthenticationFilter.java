package com.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.demo.services.AuthenticationService;
import com.demo.services.JwtService;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	AuthenticationService authenticationService;
	@Autowired
	JwtService jwtService;
	@Autowired
	UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			final String jwt = authHeader.substring(7); // Récupérer le token sans le préfixe "Bearer"
			final String userEmail = jwtService.extractUsername(jwt);

			// Vérification si le token est dans la blacklist
			if ((authenticationService.isTokenBlacklisted(jwt))) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Token invalide ou expiré.");
				return;
			}

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (userEmail != null && authentication == null) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

				// Vérification de la validité du token
				if (jwtService.isTokenValid(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());

					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}

		} catch (Exception exception) {
			logger.error("exception" + exception.getMessage());
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Token invalide ou expiré.");
			return;

		}
		filterChain.doFilter(request, response);
	}

}