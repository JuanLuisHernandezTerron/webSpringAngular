package com.notesforme.models.services;

import java.security.Key;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private final static SecretKey secrectKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	/**
	 * En este método estamos generando el token.
	 * @param usuario Recibimos por parametro toda la información por parámetro.
	 * @return devolvemos el token en forma de mapa.
	 */
	public String getToken(UserDetails usuario) {
		return getTOKEN(new HashMap<String, Object>(),usuario);
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
	private String getTOKEN(Map<String,Object> extraClaims, UserDetails user){
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(user.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
				.signWith(getKey(),SignatureAlgorithm.HS256)
				.compact();
	}

	/**
	 * @return Transformamos nuestra SecretKey en bash64 y la devolvemos
	 */
	private Key getKey() {
		return Keys.hmacShaKeyFor(secrectKey.getEncoded());
	}
}
