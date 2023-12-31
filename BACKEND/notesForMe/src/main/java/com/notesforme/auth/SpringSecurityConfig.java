package com.notesforme.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig{
		
	@Autowired
	private AuthenticationProvider authProvider;
	private final JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter();
	
	/**
	 * Solo podremos hacer las peticiones sin autenticarnos, la de login y registro del usuario,
	 * para los get, tendremos que autenticarnos
	 * @param http
	 * @return 
	 * @throws Exception
	 */
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                .csrf(csrf -> 
                    csrf
                    .disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(authRequest ->
                  authRequest
                    .requestMatchers("api/auth/cliente/**").permitAll()
                    .anyRequest().authenticated()
                    )
                .sessionManagement(sessionManager->
                    sessionManager 
                      .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); 
        return http.build();
	}
}
