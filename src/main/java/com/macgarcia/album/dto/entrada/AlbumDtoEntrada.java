package com.macgarcia.album.dto.entrada;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.macgarcia.album.entity.Album;

public class AlbumDtoEntrada implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Titulo não pode ser nulo.")
	private String titulo;
	
	@Positive(message = "Identificador inválido.")
	@NotNull(message = "Identificador do usuário esta inválido.")
	private Long idUsuario;

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

	public Album criarNovoAlbum() {
		return new Album(titulo, idUsuario);
	}

}
