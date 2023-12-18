package com.notesforme.models.services;

import java.security.Key;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
public class JwtService {

	private String secrectKey = "9a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d9";
	
    @PostConstruct
    public void init() {
        System.out.println("JwtService ha sido cargado");
    }
	
	/**
	 * *Verificamos si el token es valido
	 * @param token Contiene el token
	 * @param userDetails Contiene la información del usuario
	 * @return devuelve true o false si el username del claim y el nombre del usuario de la base de datos es igual y si el token está expirado o no.
	 */
	public boolean isValidToken(String token,UserDetails userDetails) {
		final String username = extractEmailUsername(token);
		System.out.println(userDetails);
		System.out.println(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date(0));
	}

	private java.util.Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	/*
	 * Devolvemos el token generado
	 */
	public String generatedToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	
	/**
	 * *Creamos el token, 1º Seteamos los claims con lo recibido por parametro, 
	 * 					  2º Guardamos el payload con el Subject,
	 * 					  3º Guardamos la fecha de expiración,
	 * 					  4º Guardamos la fecha de expiracion,
	 * 					  5º Firmamos el Token, donde 
	 * @param extraClaims Contiene los tipos de datos, en clave | valor (Mapa)
	 * @return
	 */
	
	public String generateToken(Map<String,Object> extraClaims,  UserDetails userdetails ) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(userdetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 *60 *24))
				.signWith(getKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	/*
	 * *Recojemos el email del token Claims::getSubject -> Es una referencia a
	 * método que obtiene el sujeto (generalmente el nombre de usuario) de los
	 * claims
	 */
	
	public String extractEmailUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * *Estaremos recogiendo los datos del token con los claims(Claims es la
	 * información que se almacenan (payload))
	 * 
	 * @param token recibe el token
	 * @return
	 */
	private Claims getTOKEN(String token) {
		return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
	}

	/**
	 * 
	 * @param <T>            Es un método publico y devuelve un tipo generico
	 * @param token          Contiene el token
	 * @param ClaimsResolver Contiene la informacion del token
	 * @return Con apply se extraen los claims y se devuelve el resultado
	 */
	public <T> T extractClaim(String token, Function<Claims, T> ClaimsResolver) {
		final Claims claims = getTOKEN(token);
		return ClaimsResolver.apply(claims);
	}

	/**
	 * @return Transformamos nuestra SecretKey en bash64 y la devolvemos
	 */
	private Key getKey() {
		byte[] byteAux = Decoders.BASE64.decode(secrectKey);
		return Keys.hmacShaKeyFor(byteAux);
	}
}
