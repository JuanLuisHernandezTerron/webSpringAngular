package com.notesforme.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.notesforme.models.entity.Nota;
import com.notesforme.models.entity.Usuario;

public interface INotaDao extends JpaRepository<Nota, Long>{
	
    @Query("SELECT n FROM Nota n WHERE n.usuario_id.id = ?1")
    public List<Nota> findByFkUsuario(String fkUsuarioId);
}
