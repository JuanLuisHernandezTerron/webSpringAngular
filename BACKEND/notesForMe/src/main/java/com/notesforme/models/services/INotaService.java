package com.notesforme.models.services;

import java.util.List;

import com.notesforme.models.entity.Nota;


public interface INotaService {
	
	public Nota findByID(Long id);
	
	public List<Nota> findAll();
	
	public Nota save(Nota notaNew);
	
	public void deleteById(Long id);
}
