package com.notesforme.controllers;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.notesforme.models.services.IUploadIMG;

import com.notesforme.models.services.INotaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.notesforme.models.entity.Nota;
import com.notesforme.models.entity.Usuario;
import com.notesforme.models.services.IUsuarioService;

@RestController
@RequestMapping("/api/notas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NotaRestController {

	@Autowired
	private IUsuarioService IUsuarioService;

	@Autowired
	private INotaService notaService;
	
	@Autowired
	private IUploadIMG ImgService;

	@GetMapping("/getAllNotas")
	public List<Nota> getNotas() {
		return notaService.findAll();
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/getNotasUser/{idUsu}")
	public ResponseEntity<?> getNotasUsuario(@PathVariable String idUsu) {
		List<Nota> listaNotas = null;
		Map<String, Object> response = new HashMap<>();
		try {
			listaNotas = notaService.findByFkUsuario(idUsu);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al hacer la consulta");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
		}

		return new ResponseEntity<List<Nota>>(listaNotas, HttpStatus.OK);
	}

	@GetMapping("/getInfoNotas/{id}")
	public ResponseEntity<?> getNotaInfo(@PathVariable long id) {
		Nota nota = null;
		Map<String, Object> response = new HashMap<>();
		try {
			nota = notaService.findByID(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al hacer la consulta");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
		}

		return new ResponseEntity<Nota>(nota, HttpStatus.OK);
	}

	@PostMapping("/insertNote")
	/**
	 * @param nota   Contiene el JSON con la informacion de la nota, he tenido que
	 *               ponerlo con un mapa ya que el atributo usuario_id es un object
	 *               y estaba pasando un string.
	 * @param result Contiene errores si algunos atributos/campos estan vacíos y si
	 *               hay errores lo devuelve en el JSON
	 * @return Devuelve un responseEntity con las respuestas o errores.
	 * @throws ParseException devuelve los error si es que hay de Date -
	 *                        SetFechaNota
	 */
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity<?> insertNota(@Valid @RequestBody Map<String, Object> nota, BindingResult result)
			throws ParseException {
		Map<String, Object> response = new HashMap<>();
		String fecha = (String) nota.get("fechaNota");
		Nota notaNew = new Nota();
		String usuarioId = (String) nota.get("fk_usuario");
		Usuario usuario = IUsuarioService.findByID(usuarioId);
		boolean borrada = (boolean) nota.get("borrada");

		notaNew.setTitulo((String) nota.get("titulo"));
		notaNew.setBorrada(borrada);
		notaNew.setDescripcion((String) nota.get("descripcion"));
		notaNew.setFechaNota(fechaCast(fecha));
		notaNew.setUsuario_id(usuario);

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(x -> "El campo '".concat(x.getField()).concat("' ," + x.getDefaultMessage()))
					.collect(Collectors.toList());
			response.put("error", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			notaService.save(notaNew);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al crear la nota");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Nota creada correctamente");
		return new ResponseEntity<Nota>(notaNew, HttpStatus.CREATED);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/deleteNote/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		try {
			notaService.deleteById(id);
		} catch (Exception e) {
			response.put("mensaje", "Error al borrar la nota");
			response.put("error", e.getMessage().concat(":")
					.concat(((NestedRuntimeException) e).getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Nota ".concat(id.toString()).concat(" eliminiada Correctamente"));
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PutMapping("/updateNota/{id}")	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity<?> updateNota(@Valid @RequestBody Nota nota, BindingResult result, @PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Nota notaAUX = notaService.findByID(id);
		List<Nota> listadoNotas = null;
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(x -> "El campo '".concat(x.getField()).concat("' ," + x.getDefaultMessage()))
					.collect(Collectors.toList());
			response.put("error", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (notaAUX == null) {
			response.put("error", "El usuario con id".concat(id.toString()).concat("No existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			notaAUX.setDescripcion(nota.getDescripcion());
			notaAUX.setTitulo(nota.getTitulo());
			notaService.save(notaAUX);
			listadoNotas = notaService.findAll();

		} catch (DataAccessException e) {
			response.put("mensake", "Error al actualizar la nota");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La nota ha sido modificada con éxito");
		response.put("Lista", listadoNotas);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PutMapping("/updateNotaBorrada")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity<?> updateNotaBorrada(@Valid @RequestBody Nota nota) {
		List<Nota> listadoNotas = notaService.findAll();
		Map<String, Object> response = new HashMap<>();
		Nota notaAUX = notaService.findByID(nota.getId());

		try {
			notaAUX.setBorrada(nota.isBorrada());
			notaService.updateNotaBorrado(notaAUX.getId(), notaAUX.isBorrada());
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la nota");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La nota ha sido modificada con éxito");
		response.put("Lista", listadoNotas);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	/**
	 * *Con este método podremos actualizar la img de perfil del usuario
	 * 
	 * @param archivo Contiene la img del formdata.
	 * @param id      Identificador del usuario
	 * @return Devuelve un mapa con los errores o si está bien con el mensaje de ok.
	 */

	@PostMapping("/insertIMG")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity<?> imgUpload(@RequestPart MultipartFile archivo, @RequestParam Long id) {
		Map<String, Object> response = new HashMap<>();
		Nota notaActual = notaService.findByID(id);
		String nombreArchivo = null;
		if (!archivo.isEmpty()) {
			try {
				nombreArchivo = ImgService.copiar(archivo);
			} catch (IOException e) {
				response.put("mensaje", "La imagen no ha podido ser subida ".concat(nombreArchivo));
				response.put("error", e.getMessage());
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			/**
			 * Para eliminar la img cuando la actualizemos. 1º Guardamos la ruta de la
			 * anterior IMG 2º Si es no es null entra dentro del if. 3º Cojemos la ruta de
			 * la imagen anterior 4º Pasamos esa ruta a un archivo 5º Si existes y se puede
			 * leer dentra dentro del if y eliminamos la imagen
			 **/
			String fotoAnterior = notaActual.getImgNota();
			ImgService.eliminar(fotoAnterior);

			notaActual.setImgNota(nombreArchivo);
			notaService.save(notaActual);
			response.put("nota", notaActual);
			response.put("mensaje", "La nota con id : " + notaActual.getId());
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/busquedaAvanzada")
	public ResponseEntity<?>BusquedaAvanzada(@RequestParam String idUsuario,@RequestParam String valor){
		Map<String, Object> response = new HashMap<>();
		List<Nota> notaLista = null;
		
		try {
			notaLista = notaService.busquedaAvanzada(idUsuario, valor);
		} catch (Exception e) {
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		response.put("notas", notaLista);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}

	private java.sql.Date fechaCast(String fecha) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date fechaUtil = format.parse(fecha);
		return new java.sql.Date(fechaUtil.getTime());
	}
}
