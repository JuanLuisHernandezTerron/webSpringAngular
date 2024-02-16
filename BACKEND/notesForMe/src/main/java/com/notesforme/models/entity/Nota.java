package com.notesforme.models.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Temporal;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="notas")
public class Nota implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "El titulo no puede estar vacío")
	private String titulo_nota;

	@NotEmpty(message = "La descripción no puede estar vacío")
	private String descripcion_nota;
	
	private boolean borrada = false;

	@Column(name = "fecha_publicacion")
	private Date fechaNota;
	
	private String imgNota;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_usuario")
	@JsonBackReference
	private Usuario usuario_id;

	public Usuario getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(Usuario usuario_id) {
		this.usuario_id = usuario_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo_nota;
	}

	public Nota() {
		super();
	}

	public void setTitulo(String titulo) {
		this.titulo_nota = titulo;
	}

	public String getDescripcion() {
		return descripcion_nota;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion_nota = descripcion;
	}

	public Date getFechaNota() {
		return fechaNota;
	}

	public void setFechaNota(Date fechaNota) {
		this.fechaNota = fechaNota;
	}

	public boolean isBorrada() {
		return borrada;
	}

	public void setBorrada(boolean borrada) {
		this.borrada = borrada;
	}

	public String getImgNota() {
		return imgNota;
	}

	public void setImgNota(String imgNota) {
		this.imgNota = imgNota;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
