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

	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = null;
		try {
			System.out.println(usuarioDao);
			usuario = usuarioDao.findByEmail(username);
			System.out.println(usuario);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new UsernameNotFoundException("Usuario No encontrado");
		}
		UserDetails User = usuario;
		System.out.println(User);
		return User;
	}
}
