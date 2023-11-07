package com.notesforme.models.services;

import java.util.List;

import com.notesforme.models.entity.Usuario;

public interface IUsuarioService {
	public List<Usuario> findAll();
	
	public Usuario findByID(String id);
	
	public Usuario save(Usuario usuario);
	
	public void delete(String id);
}
