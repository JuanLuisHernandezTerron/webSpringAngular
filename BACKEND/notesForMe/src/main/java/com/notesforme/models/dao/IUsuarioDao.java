package com.notesforme.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.notesforme.models.entity.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario, String>{

}
