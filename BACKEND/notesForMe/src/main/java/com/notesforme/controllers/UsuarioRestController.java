package com.notesforme.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	 * Utilizamos ResponseEntity PARA EL MANEJO DE ERRORES, POSTERIORMENTE ENTRE <> PONEMOS ?, ya que se puede asociar a un obejto,string etc..
	 * ?(Sería como el any, un tipo genérico).
	 * 
	 */
	@GetMapping("/getInfoUser/{id}")
	public ResponseEntity<?> getInfoUser(@PathVariable String id) {
		Usuario usuario = null;
		Map<String, Object> response = new HashMap<>();
		try {
			usuario = UsuarioService.findByID(id);
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al hacer la consulta");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (usuario == null) {
			response.put("mensaje", "El usuario con el id:".concat(id.toString()).concat(", no ha sido encontrado en la base de datos"));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);		
		}
	
		return new ResponseEntity<Usuario>(usuario,HttpStatus.OK);
	}
	
	
	@PostMapping("/inserUser")
	/*
	* Utilizamos @ResponseStatus para que el codigo 201 quede OK en la peticion.
	* Utilizamos @RequestBody para cojer el json que nos envia el front
	*/
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> SaveUser(@RequestBody Usuario usuario) {
		Map<String, Object> response = new HashMap<>();
		Usuario usuarioNew = null;
		try {
			usuarioNew = UsuarioService.save(usuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al crear el usuario");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Usuario>(usuarioNew,HttpStatus.CREATED);
	}
	
	/*
	 * Al no retornar el DELETE ningun contenido, podemos poner que la respuesta sea No content
	 */
	@DeleteMapping("/deleteUser/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable String id) {
		UsuarioService.delete(id);
	}
	
	/*
	 * En spring no hay metodos para updatear, se tiene que setear el objeto de la base de datos y posteriormente lo actualizamos con el metodo
	 * save(), que también sirve para el post.
	 */
	@PutMapping("/updateUser/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario updateUser(@RequestBody Usuario usuario,@PathVariable String id) {
		 Usuario usuarioAUX = UsuarioService.findByID(id);
		 usuarioAUX.setApellidos(usuario.getApellidos());
		 usuarioAUX.setContrasena(usuario.getContrasena());
		 usuarioAUX.setDni(usuario.getDni());
		 usuarioAUX.setEmail(usuario.getEmail());
		 usuarioAUX.setFechaNacimiento(usuario.getFechaNacimiento());
		 usuarioAUX.setNombre(usuario.getNombre());
		 System.out.print("Actualizado Correctamente");
		 return UsuarioService.save(usuarioAUX);
	}

}
