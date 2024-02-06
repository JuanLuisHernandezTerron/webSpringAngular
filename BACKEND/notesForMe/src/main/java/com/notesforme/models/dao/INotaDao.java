package com.notesforme.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.notesforme.models.entity.Nota;

public interface INotaDao extends JpaRepository<Nota, Long>{
	
	@Transactional
	@Modifying
	@Query("update Nota n SET n.borrada=:borrada where n.id=:idNota")
	public void updateBorradoNota(@Param("idNota") Long idNota, @Param("borrada") boolean borrada);
	
    @Query("SELECT n FROM Nota n WHERE n.usuario_id.id = ?1")
    public List<Nota> findByFkUsuario(String fkUsuarioId);
}
