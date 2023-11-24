package com.notesforme.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.notesforme.models.dao.INotaDao;
import com.notesforme.models.entity.Nota;

public class INotaServiceImpl implements INotaService{

	@Autowired
	private INotaDao notaDao;
	
	@Override
	public Nota findByID(Long id) {
		return notaDao.findById(id).orElse(null);
	}

	@Override
	public List<Nota> findAll() {
		return notaDao.findAll();
	}

	@Override
	public Nota save(Nota nota) {
		return notaDao.save(nota);
	}

	@Override
	public void deleteById(Long id) {
		notaDao.deleteById(id);
	}
}
