package com.notesforme.controllers;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.notesforme.models.dao.IUsuarioDao;
import com.notesforme.models.entity.Usuario;
import com.notesforme.models.entity.loginRequest;
import com.notesforme.models.services.IUploadIMG;
import com.notesforme.models.services.IUsuarioService;
import com.notesforme.models.services.JwtService;
import com.notesforme.Bcrypt.serviceBcrypt;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UsuarioRestController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private IUsuarioDao IDaoUser;
	
	@Autowired
	private IUsuarioService UsuarioService;
	
	@Autowired
	private IUploadIMG ImgService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private serviceBcrypt BcryptService;

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
	
	/**
	 * *Devuelvo el token, que en este caso es AuthResponse
	 * @param login
	 * @return
	 */
	@PostMapping("/auth/cliente/loginUser")
	public ResponseEntity<?> loginUser(@Valid @RequestBody loginRequest login){
		Map<String, Object> response = new HashMap<>();
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getContrasena()));			
		} catch (DisabledException e) {
			response.put("mensaje", e.getMessage());
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		var user = IDaoUser.findByEmail(login.getEmail());
		String token = jwtService.generatedToken(user);
		if (BcryptService.verifyPasswd(login.getContrasena(), user.getPassword()) == true) {
			response.put("token",token);
		}else{
			response.put("mensaje","Datos introducidos incorrectamente!");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	
	@PostMapping("/auth/cliente/registerUser")
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
			usuario.setContrasena(BcryptService.contrasenaEncrypt(usuario.getContrasena()));
			usuarioNew = UsuarioService.save(usuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al crear el usuario");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("token",jwtService.generatedToken(usuarioNew));
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	/*
	 * Al no retornar el DELETE ningun contenido, podemos poner que la respuesta sea No content
	 */
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable String id) {
		Map<String, Object> response = new HashMap<>();
		Usuario usuarioActual = UsuarioService.findByID(id);
		try {
			String fotoAnterior = usuarioActual.getImgPerfil();
			/**
			 * Eliminamos la imagen
			 */
			ImgService.eliminar(fotoAnterior);
			
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
	
	/**
	 * *Con este método podremos actualizar la img de perfil del usuario
	 * @param archivo Contiene la img del formdata.
	 * @param id Identificador del usuario
	 * @return Devuelve un mapa con los errores o si está bien con el mensaje de ok.
	 */
	
	@PostMapping("/cliente/insertIMG")
	public ResponseEntity<?>imgUpload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") String id){
		Map<String, Object> response = new HashMap<>();
		Usuario usuarioActual = UsuarioService.findByID(id);
		String nombreArchivo = null;
		if (!archivo.isEmpty()) {
			try {
				nombreArchivo =	ImgService.copiar(archivo);
			} catch (IOException e) {
				response.put("mensaje", "La imagen no ha podido ser subida ".concat(nombreArchivo));
				response.put("error", e.getMessage());
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			/**
			 * Para eliminar la img cuando la actualizemos.
			 * 1º Guardamos la ruta de la anterior IMG
			 * 2º Si es no es null entra dentro del if.
			 * 3º Cojemos la ruta de la imagen anterior
			 * 4º Pasamos esa ruta a un archivo
			 * 5º Si existes y se puede leer dentra dentro del if y eliminamos la imagen
			 **/
			String fotoAnterior = usuarioActual.getImgPerfil();
			ImgService.eliminar(fotoAnterior);
			
			usuarioActual.setImgPerfil(nombreArchivo);
			UsuarioService.save(usuarioActual);
			response.put("cliente", usuarioActual);
			response.put("mensaje", "La imagen ha sido ingresada correctamente, cliente: "+usuarioActual.getNombre());
		}
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/cliente/img/uploads/{nombreImagen:.+}")
	public ResponseEntity<Resource>verIMG(@PathVariable String nombreImagen){
		Path rutaImgAntigua = Paths.get("uploads").resolve(nombreImagen).toAbsolutePath();
		Resource resource = null;
		try {
			resource = new UrlResource(rutaImgAntigua.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		if (!resource.exists() && !resource.isReadable()) {
			throw new RuntimeException("No se ha podido cargar la imagen");
		}
		
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"");

		return new ResponseEntity<Resource>(resource,cabecera,HttpStatus.OK);
	}
	

}
