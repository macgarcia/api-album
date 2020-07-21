package com.macgarcia.album.services;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.Part;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.macgarcia.album.dto.saida.FotoDtoSaida;
import com.macgarcia.album.entity.Album;
import com.macgarcia.album.entity.Foto;
import com.macgarcia.album.repository.AlbumRepository;
import com.macgarcia.album.repository.FotoRepository;

@Service
public class FotoService {
	
	private AlbumRepository albumDao;
	private FotoRepository dao;
	
	@Autowired
	public FotoService(AlbumRepository albumDao, FotoRepository dao) {
		this.albumDao = albumDao;
		this.dao = dao;
	}

	public Optional<Foto> montarFoto(String descricao, Long idAlbum, Part arquivo) {
		if (arquivo.getContentType() != null) {
			String tipo = arquivo.getContentType();
			long tamanho = arquivo.getSize();
			try {
				InputStream is = arquivo.getInputStream();
				int count = 0;
				int index = 0;
				byte[] b = new byte[(int) arquivo.getSize()];
				while (count < b.length && (index = is.read(b, count, b.length - count)) >= 0) {
					count += index;
				}
				return construir(descricao, idAlbum, tipo, tamanho, b);
			} catch (Exception e) {
				return Optional.empty();
			}
		}
		return Optional.empty();
	}

	private Optional<Foto> construir(String descricao, Long idAlbum, String tipo, long tamanho, byte[] b) {
		Album album = albumDao.findById(idAlbum).get();
		Foto novaFoto = new Foto(descricao, album, tipo, tamanho, b);
		return Optional.of(novaFoto);
	}

	@Transactional
	public boolean salvar(Foto foto) {
		try {
			dao.save(foto);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean verificarExistencia(Long idFoto) {
		return dao.existsById(idFoto);
	}

	@Transactional
	public boolean excluirFoto(Long idFoto) {
		try {
			dao.deleteById(idFoto);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public Page<FotoDtoSaida> buscarFotos(Long idAlbum, Pageable page) {
		var fotos = dao.findByIdAlbum(idAlbum, page);
		List<FotoDtoSaida> collect = fotos.stream().map(e -> {return new FotoDtoSaida(e);}).collect(Collectors.toList());
		return new PageImpl<FotoDtoSaida>(collect, fotos.getPageable(), fotos.getTotalElements());
	}

	@Transactional
	public FotoDtoSaida buscarFoto(Long idFoto) {
		var foto = dao.buscarUnicaFoto(idFoto);
		return new FotoDtoSaida(foto);
	}

}
