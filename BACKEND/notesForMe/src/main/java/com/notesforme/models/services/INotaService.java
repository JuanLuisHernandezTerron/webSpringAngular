package com.notesforme.models.services;

import java.util.List;

import com.notesforme.models.entity.Nota;
import com.notesforme.models.entity.busquedaAvanzada;

public interface INotaService {
	
	public Nota findByID(Long id);
	
	public List<Nota> findAll();
	
	public Nota save(Nota notaNew);
	
	public void deleteById(Long id);
	
	public List<Nota> findByFkUsuario(String usuario);
	
	public void updateNotaBorrado(Long idNota, boolean borrada);
	
	public List<Nota> busquedaAvanzada(String idUsuario, String value);
}
