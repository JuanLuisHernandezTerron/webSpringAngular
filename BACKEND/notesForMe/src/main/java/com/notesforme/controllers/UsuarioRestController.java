package com.notesforme.controllers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

import jakarta.validation.Valid;


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
	
	/**
	 * Metodo donde devolvemos la información paginada.
	 * @param numeroPag Contiene el numero de página que queremos que nos devuelva
	 * @return Le pasamos por parametros al PageRequest.of, el numero de página que queremos que nos muestre y cuantos usuario
	 * quiere que nos devuelva por cada página, hace el offset interiormente.
	 */
	
	@GetMapping("/getInfoUser/page/{numeroPag}")
	public Page<Usuario> PageUser(@Valid @PathVariable int numeroPag){
		return UsuarioService.findAll(PageRequest.of(numeroPag, 5));
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
	public ResponseEntity<?> SaveUser(@Valid @RequestBody Usuario usuario, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		Usuario usuarioNew = null;
		/*
		 * IMPORTANTE, LA FUNCION FLECHA EN JAVA ES -> ¡¡NO =>!! 
		 *Si hay errores, entrará dentro del if y
		 *1º Creamos una lista de string donde contendrá una lista de errores de la validacion en el modelo de Usuario.
		 *2º Convertimos la lista a stream, para poder tratar los datos.
		 *3º Utilizamos un map para recorrerlos, y cada error que haya, pondremos un string de aviso.
		 *4º Utilizamos collet, para poder volver a transfomar este stream a un tipo collection de lista.
		 *5º Y por ultimo lo devolvemos para que el frontend pueda mostrarse los errores de validacion del modelo de Usuario.
		 */
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(x -> "El campo '".concat(x.getField()).concat("' ,"+x.getDefaultMessage()))
					.collect(Collectors.toList());
			response.put("error", errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
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
	public ResponseEntity<?> deleteUser(@PathVariable String id) {
		Map<String, Object> response = new HashMap<>();
		try {
			UsuarioService.deleteById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el usuario");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);		
		}
		response.put("mensaje", "El usuario con id ".concat(id).concat(", ha sido borrado CORRECTAMENTE"));
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	
	/*
	 * En spring no hay metodos para updatear, se tiene que setear el objeto de la base de datos y posteriormente lo actualizamos con el metodo
	 * save(), que también sirve para el post.
	 * 
	 * PONER EL BINDING RESULT, DETRÁS DEL ¡PATHVARIABLE!
	 */
	@PutMapping("/updateUser/{id}")
	public ResponseEntity<?> updateUser(@Valid @RequestBody Usuario usuario,BindingResult result,@PathVariable String id) {
		Map<String, Object> response = new HashMap<>();
		Usuario usuarioActual = UsuarioService.findByID(id);
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(x -> "El campo '".concat(x.getField()).concat("' ,"+x.getDefaultMessage()))
					.collect(Collectors.toList());
			response.put("error", errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		if (usuario == null) {
			response.put("mensaje", "El usuario con el id:".concat(id.toString()).concat(", no ha sido encontrado en la base de datos, y no se ha podido actualizar"));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);		
		}
		try {
			usuarioActual.setApellidos(usuario.getApellidos());
			usuarioActual.setContrasena(usuario.getContrasena());
			usuarioActual.setDni(usuario.getDni());
			usuarioActual.setEmail(usuario.getEmail());
			usuarioActual.setFechaNacimiento(usuario.getFechaNacimiento());
			usuarioActual.setNombre(usuario.getNombre());
			UsuarioService.save(usuarioActual);
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el usuario");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Usuario>(usuarioActual,HttpStatus.CREATED);
	}

}
