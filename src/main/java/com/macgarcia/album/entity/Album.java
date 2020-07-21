package com.macgarcia.album.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ALBUM")
public class Album extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "TITULO", nullable = false)
	private String titulo;

	@Column(name = "USUARIO_ID", nullable = false)
	private Long idUsuario;
	
	
	@Deprecated
	public Album() {}

	public Album(String titulo, Long idUsuario) {
		this.titulo = titulo;
		this.idUsuario = idUsuario;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

}
