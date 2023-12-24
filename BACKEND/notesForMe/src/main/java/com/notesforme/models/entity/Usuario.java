package com.notesforme.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Temporal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable,UserDetails {

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
	@Column(length = 60)
	private String contrasena;
	
	private Role role;
	
	@NotNull(message = "La fecha no puede ser nula")
	@Column(name = "fecha_nacimiento")
	private Date FechaNacimiento;
	
	private String img_Perfil;
	private boolean enabled;
	
	//Utilizamos carga perezosa, para que solo lo utilizemos cuando sea necesario y se llame solo a ese método.
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@JsonManagedReference
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
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		return contrasena;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


}
