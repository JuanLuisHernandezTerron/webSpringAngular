package com.notesforme.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.notesforme.models.entity.Usuario;
import com.notesforme.models.services.IUsuarioService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class UsuarioRestController {
	@Autowired
	private IUsuarioService UsuarioService;

	@GetMapping("/getAllUsuarios")
	public List<Usuario> getUsuarios() {
		return UsuarioService.findAll();
	}

	/*
	 * Utilizamos @PathVariable para recojer el id del parametro
	 */

	@GetMapping("/getInfoUser/{id}")
	public Usuario getInfoUser(@PathVariable String id) {
		return UsuarioService.findByID(id);
	}
	
	@PutMapping("/inserUser")

	/*
	* Utilizamos @ResponseStatus para que el codigo 201 quede OK en la peticion.
	* Utilizamos @RequestBody para cojer el json que nos envia el front
	*/
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario SaveUser(@RequestBody Usuario usuario) {
		return UsuarioService.save(usuario);
	}
	
	
	@DeleteMapping("/deleteUser/{id}")
	public void deleteUser(@PathVariable String id) {
		UsuarioService.delete(id);
	}

}
