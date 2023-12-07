package com.notesforme.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.notesforme.models.entity.Usuario;

public interface IUsuarioService{
	public List<Usuario> findAll();
	
	/**
	 * Paginacion de usuario para el administrador.
	 * @return Devolvera un page que es parecido a un list, pero entre rangos ya que es una paginación.
	 */
	public Page<Usuario> findAll(Pageable pageable);
	
	public Usuario findByID(String id);
	
	public Usuario save(Usuario usuario);
	
	public void deleteById(String id);
}
