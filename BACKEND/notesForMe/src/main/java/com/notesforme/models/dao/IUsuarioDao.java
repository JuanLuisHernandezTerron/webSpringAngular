package com.notesforme.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.notesforme.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, String>{

}
