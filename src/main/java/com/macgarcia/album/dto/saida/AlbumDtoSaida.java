package com.macgarcia.album.dto.saida;

import java.io.Serializable;

import com.macgarcia.album.entity.Album;

public class AlbumDtoSaida implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String titulo;

	public AlbumDtoSaida(Album e) {
		this.id = e.getId();
		this.titulo = e.getTitulo();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
