package com.notesforme.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig{
	
	
	/*private JWTAuthenticationFilter jwtAuthenticationFilter;*/
	
	@Autowired
	private AuthenticationProvider authProvider;
	
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
                .csrf(csrf -> 
                    csrf
                    .disable())
                .authorizeHttpRequests(authRequest ->
                  authRequest
                    .requestMatchers("api/auth/cliente/**").permitAll()
                    .anyRequest().authenticated()
                    )
                .sessionManagement(sessionManager->
                    sessionManager 
                      .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                /*.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)*/
                .getOrBuild();
	}
}
