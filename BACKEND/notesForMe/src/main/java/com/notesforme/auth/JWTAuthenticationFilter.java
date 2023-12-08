package com.notesforme.auth;

import java.io.IOException;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends OncePerRequestFilter{

	
	/**
	 * Extendemos de OncePerRequestFilter, para hacer filtros personalizados y que solo lo haga 1 vez por petición,
	 * primero vemos si el token es nulo, si es nulo, cada vez que hacen una petición, filterChain permite que el filtro pase la
	 * solicitud y la respuesta al siguiente camino.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {	
		final String token = getTokenRequest(request);
				
		if (token == null) {
			filterChain.doFilter(request, response);
			return;
		}
		filterChain.doFilter(request, response);
	}
	
	/**
	 * *Con este método estaremos recogiendo el token, si tiene la autenticación y tiene el inicio de la palabra Bearer
	 * @param request contiene toda la información de la cabezera
	 * @return devuelve el token o null dependiendo si entra en el if o no.
	 */
	private String getTokenRequest(HttpServletRequest request) {
		final String authHeader=request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);
		if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}
		return null;
	}
}
