package com.notesforme.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notesforme.models.entity.Nota;

public interface INotaDao extends JpaRepository<Nota, Long>{

}
