package com.notesforme.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notesforme.models.dao.INotaDao;
import com.notesforme.models.entity.Nota;
import com.notesforme.models.entity.Usuario;
import com.notesforme.models.entity.busquedaAvanzada;

@Service 
public class INotaServiceImpl implements INotaService{

	@Autowired
	private INotaDao notaDao;
	
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

	@Override
	public Nota findByID(Long id) {
		return notaDao.findById(id).orElse(null);
	}

	@Override
	public List<Nota> findByFkUsuario(String id_usuario) {
		return notaDao.findByFkUsuario(id_usuario);
	}

	@Override
	public void updateNotaBorrado(Long idNota, boolean borrada) {
		notaDao.updateBorradoNota(idNota, borrada);
	}

	@Override
	public List<Nota> busquedaAvanzada(String idUsuario, String value) {
		return notaDao.busquedaAvanzada(idUsuario,value);
	}
}
