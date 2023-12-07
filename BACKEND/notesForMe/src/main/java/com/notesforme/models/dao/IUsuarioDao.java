package com.notesforme.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notesforme.models.entity.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario, String>{
	public Usuario findByEmail(String email);
}
