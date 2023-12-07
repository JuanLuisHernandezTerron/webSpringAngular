package com.notesforme.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig{

	private static JWTAuthenticationFilter jwtAuthenticationFilter;
	private static AuthenticationProvider authProvider;
	
	/**
	 * Solo podremos hacer las peticiones sin autenticarnos, la de login y registro del usuario,
	 * para los get, tendremos que autenticarnos
	 * @param http
	 * @return 
	 * @throws Exception
	 */
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csfr-> 
						csfr.disable())
				.authorizeHttpRequests(auth -> auth
												.requestMatchers("/api/auth/cliente/**")
												.permitAll()
												.anyRequest()
												.authenticated())
				.sessionManagement(sessionManager ->
							sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				/**.authenticationProvider(authProvider)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)**/
				.build();
	}
}
