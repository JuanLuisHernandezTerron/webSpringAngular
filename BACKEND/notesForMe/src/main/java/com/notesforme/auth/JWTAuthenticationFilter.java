package com.notesforme.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.notesforme.models.services.JwtService;
import com.notesforme.models.services.UserServiceDetailsImp;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtService IjwtService = new JwtService();
	
	@Autowired
	private UserServiceDetailsImp userDetailsService = new UserServiceDetailsImp();

    @PostConstruct
    public void init() {
        System.out.println("JWTAuthenticationFilter ha sido cargado");
    }
	
	/**
	 * Extendemos de OncePerRequestFilter, para hacer filtros personalizados y que solo lo haga 1 vez por petición,
	 * primero vemos si el token es nulo, si es nulo, cada vez que hacen una petición, filterChain permite que el filtro pase la
	 * solicitud y la respuesta al siguiente camino.
	 * 
	 * Simplemente viendo si el usuario tiene un token valido, establecemos la autenticacion creando un nuevo usernamepasswordAuthentication con los detalles
	 * del usuario y al final pasamos la solicitud y la respuesta al siguiente filtro.
	 */
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		String token= "";	
		String username= "";
		if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
		}else if(token == null || authHeader == null){
			filterChain.doFilter(request, response);
			return;
		}
		username = IjwtService.extractEmailUsername(token);	
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if (IjwtService.isValidToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
