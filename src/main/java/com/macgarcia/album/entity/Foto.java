package com.macgarcia.album.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "FOTO")
public class Foto extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "DESCRICAO", nullable = false)
	private String descricao;

	@Column(name = "TAMANHO", nullable = false)
	private Long tamanho;

	@Column(name = "TIPO", nullable = false)
	private String tipo;

	@CreationTimestamp
	@Column(name = "DATA_UPLOAD", nullable = false)
	private LocalDate dataUpload;

	@Lob
	@Column(name = "arquivo", nullable = false)
	private byte[] arquivo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ALBUM_ID")
	@JsonIgnore
	private Album album;

	@Deprecated
	public Foto() {
	}

	public Foto(String descricao, Album album, String tipo, long tamanho, byte[] b) {
		this.descricao = descricao;
		this.album = album;
		this.tipo = tipo;
		this.tamanho = tamanho;
		this.arquivo = b;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
