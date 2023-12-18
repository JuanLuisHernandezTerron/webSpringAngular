package com.notesforme.models.dao;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.notesforme.models.entity.Usuario;
@ComponentScan(basePackages = "com.notesforme")
@Repository
public interface IUsuarioDao extends JpaRepository<Usuario, String>{
	public Usuario findByEmail(String email);
}
