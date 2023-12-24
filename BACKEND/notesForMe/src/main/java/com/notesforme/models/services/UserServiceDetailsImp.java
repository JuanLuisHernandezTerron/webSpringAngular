package com.notesforme.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.notesforme.models.dao.IUsuarioDao;
import com.notesforme.models.entity.Usuario;

@Service
public class UserServiceDetailsImp implements UserDetailsService{
	
	Usuario usuario = null;
	private static IUsuarioDao usuDao;
	
	public IUsuarioDao getUsuarioDao() {
		return usuDao;
	}
	
	@Autowired
	public void setUsuarioDao(IUsuarioDao usuario) {
		UserServiceDetailsImp.usuDao = usuario;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			usuario = getUsuarioDao().findByEmail(username);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new UsernameNotFoundException("Usuario No encontrado");
		}
		UserDetails User = usuario;
		return User;
	}
}
