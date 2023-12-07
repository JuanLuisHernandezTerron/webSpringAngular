package com.notesforme.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.notesforme.models.dao.IUsuarioDao;
import com.notesforme.models.entity.Usuario;


@Service
public class IUsuarioServiceImpl implements IUsuarioService{
	
	@Autowired
	private IUsuarioDao usuarioDao; 
	
	@Override
	public List<Usuario> findAll() {
		return (List<Usuario>) usuarioDao.findAll();
	}

  	@Override
	public Page<Usuario> findAll(Pageable pageable) {
		return usuarioDao.findAll(pageable);
	}
	
	@Override
	public Usuario findByID(String id) {
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	public Usuario save(Usuario usuario) {
		return usuarioDao.save(usuario);
	}

	@Override
	public void deleteById(String id) {
		usuarioDao.deleteById(id);
	}

	/**@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioDao.findByEmail(username);
		
		if (usuario == null) {
			logger.error("ERROR EN EL LOGIN: Usuario No encontrado");
			throw new UsernameNotFoundException("ERROR EN EL LOGIN: Usuario No encontrado");
		}
		
		return new User(usuario.getEmail(), usuario.getContrasena(), usuario.isEnabled(), true, true, true, null);
	}**/
}
