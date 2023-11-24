package com.notesforme.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

	@Id
	private String dni;
	
	@Column(nullable = false)
	@NotEmpty
	@Size(min = 3, max = 20)
	private String nombre;
	
	@NotEmpty
	@Size(min = 5, max = 20)
	private String apellidos;
	
	@Column(nullable = false, unique = true)
	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	private String contrasena;
	
	@NotNull(message = "La fecha no puede ser nula")
	@Column(name = "fecha_nacimiento")
	private Date FechaNacimiento;
	
	private String img_Perfil;
	
	//Utilizamos carga perezosa, para que solo lo utilizemos cuando sea necesario y se llame solo a ese método.
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private List<Nota> nota = new ArrayList<Nota>();


	public List<Nota> getNota() {
		return nota;
	}

	public void setNota(List<Nota> nota) {
		this.nota = nota;
	}

	public Usuario() {
		super();
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Date getFechaNacimiento() {
		return FechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		FechaNacimiento = fechaNacimiento;
	}

	public String getImgPerfil() {
		return img_Perfil;
	}

	public void setImgPerfil(String imgPerfil) {
		this.img_Perfil = imgPerfil;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
