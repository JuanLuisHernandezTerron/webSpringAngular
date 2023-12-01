package com.notesforme.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataAccessException;
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
import org.springframework.web.bind.annotation.RestController;
import com.notesforme.models.services.INotaService;

import jakarta.validation.Valid;

import com.notesforme.models.entity.Nota;
import com.notesforme.models.entity.Usuario;
import com.notesforme.models.services.IUsuarioService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/notas")
public class NotaRestController {
	
	@Autowired
	private IUsuarioService IUsuarioService;
	
	@Autowired 
	private INotaService notaService;
	
	@GetMapping("/getAllNotas")
	public List<Nota> getNotas(){
		return notaService.findAll();
	}
	
	@GetMapping("/getInfoNotas/{id}")
	public ResponseEntity<?>getNotaInfo(@PathVariable long id){
		Nota nota = null;
		Map<String, Object> response = new HashMap<>();
		try {
			nota = notaService.findByID(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al hacer la consulta");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
		}
		
		return new ResponseEntity<Nota>(nota,HttpStatus.OK);
	}
	
	@PostMapping("/insertNote")
	/**
	 * @param nota Contiene el JSON con la informacion de la nota, he tenido que ponerlo con un mapa ya que el atributo usuario_id es un object y estaba
	 * pasando un string.
	 * @param result Contiene errores si algunos atributos/campos estan vacíos y si hay errores lo devuelve en el JSON
	 * @return Devuelve un responseEntity con las respuestas o errores.
	 * @throws ParseException devuelve los error si es que hay de Date - SetFechaNota
	 */
	public ResponseEntity<?> insertNota(@Valid @RequestBody Map<String, Object> nota, BindingResult result) throws ParseException{
		Map<String, Object> response = new HashMap<>();
		String fecha = (String) nota.get("fechaNota");
		Nota notaNew = new Nota();
		String usuarioId = (String) nota.get("fk_usuario");
		Usuario usuario = IUsuarioService.findByID(usuarioId);
		
		notaNew.setTitulo((String) nota.get("titulo"));
		notaNew.setDescripcion((String) nota.get("descripcion"));
		notaNew.setFechaNota(fechaCast(fecha));
		notaNew.setUsuario_id(usuario);
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(x -> "El campo '".concat(x.getField()).concat("' ,"+x.getDefaultMessage()))
					.collect(Collectors.toList());
			response.put("error", errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		try {
			notaService.save(notaNew);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al crear el usuario");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Nota creada correctamente");
		return new ResponseEntity<Nota>(notaNew,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/deleteNote/{id}")
	public ResponseEntity<?>deleteNote(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		
		try {
			notaService.deleteById(id);	
		} catch (Exception e) {
			response.put("mensaje", "Error al borrar la nota");
			response.put("error", e.getMessage().concat(":").concat(((NestedRuntimeException) e).getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Nota ".concat(id.toString()).concat(" eliminiada Correctamente"));
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@PutMapping("/updateNota/{id}")
	public ResponseEntity<?>updateNota(@Valid @RequestBody Nota nota,BindingResult result,@PathVariable Long id){
		Map<String,Object> response = new HashMap<>();
		Nota notaAUX = notaService.findByID(id);
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(x -> "El campo '".concat(x.getField()).concat("' ,"+x.getDefaultMessage()))
					.collect(Collectors.toList());
			response.put("error", errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		if (notaAUX == null) {
			response.put("error", "El usuario con id".concat(id.toString()).concat("No existe en la base de datos"));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try {
			notaAUX.setDescripcion(nota.getDescripcion());
			notaAUX.setTitulo(nota.getTitulo());
			notaService.save(notaAUX);
		} catch (DataAccessException e) {
			response.put("mensake", "Error al actualizar la nota");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		 response.put("mensaje","La nota ha sido modificada con éxito");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	
	private java.sql.Date fechaCast(String fecha) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date fechaUtil = format.parse(fecha);
		return new java.sql.Date(fechaUtil.getTime());
	}
}
