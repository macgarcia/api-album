package com.macgarcia.album.dto.saida;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.macgarcia.album.entity.Foto;

public class FotoDtoSaida implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String descricao;
	private String tipo;
	private Long tamanho;
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate dataUpload;
	private byte[] arquivo;

	public FotoDtoSaida(Foto e) {
		this.id = e.getId();
		this.descricao = e.getDescricao();
		this.tipo = e.getTipo();
		this.tamanho = e.getTamanho();
		this.dataUpload = e.getDataUpload();
		this.arquivo = e.getArquivo();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getTamanho() {
		return tamanho;
	}

	public void setTamanho(Long tamanho) {
		this.tamanho = tamanho;
	}

	public LocalDate getDataUpload() {
		return dataUpload;
	}

	public void setDataUpload(LocalDate dataUpload) {
		this.dataUpload = dataUpload;
	}

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

}
