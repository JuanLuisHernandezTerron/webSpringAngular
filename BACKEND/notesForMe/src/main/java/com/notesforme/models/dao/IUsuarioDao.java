package com.notesforme.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.notesforme.models.entity.Usuario;

@Repository
public interface IUsuarioDao extends JpaRepository<Usuario, String>{
	public Usuario findByEmail(String email);
}
