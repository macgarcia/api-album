package com.macgarcia.album.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.macgarcia.album.dto.entrada.AlbumDtoEntrada;
import com.macgarcia.album.dto.saida.AlbumDtoSaida;
import com.macgarcia.album.entity.Album;
import com.macgarcia.album.repository.AlbumRepository;

@Service
public class AlbumService {
	
	//Utilizados para quando há a exclusão do album, mesmo contendo fotos.
	private final String CONTAGEM_DE_REGISTROS = "select count(*) from Foto f where f.album.id = :idAlbum";
	private final String EXCLUIR_REGISTROS     = "delete from Foto f where f.album.id = :idAlbum";

	private AlbumRepository dao;
	private Validator validator;
	private String mensagemDeErro;
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	public AlbumService(AlbumRepository dao, Validator validator) {
		this.dao = dao;
		this.validator = validator;
	}

	@Transactional
	public Page<AlbumDtoSaida> buscarAlbuns(Long idUsuario, Pageable page) {
		var albuns = dao.findByIdUsuario(idUsuario, page);
		List<AlbumDtoSaida> result = albuns.stream()
				.map(e -> {return new AlbumDtoSaida(e);})
				.collect(Collectors.toList());
		return new PageImpl<AlbumDtoSaida>(result, page, albuns.getTotalElements());
	}
	
	@Transactional
	public boolean salvar(AlbumDtoEntrada dto) {
		try {
			Album novoAlbum = dto.criarNovoAlbum();
			dao.saveAndFlush(novoAlbum);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean validar(AlbumDtoEntrada dto) {
		Set<ConstraintViolation<AlbumDtoEntrada>> erros = validator.validate(dto);
		if (!erros.isEmpty()) {
			mensagemDeErro = erros.stream().map(e -> e.getPropertyPath().toString().toUpperCase() + ": " 
					+ e.getMessageTemplate()).collect(Collectors.joining("\n"));
			return false;
		}
		return true;
	}
	
	public String getMensagemDeErro() {
		return mensagemDeErro;
	}

	public boolean verificarExistencia(Long idAlbum) {
		return dao.existsById(idAlbum);
	}

	@Transactional
	//Deve excluir todas as fotos do album, antes de excluir o album.
	public boolean excluirAlbum(Long idAlbum) {
		try {
			TypedQuery<Long> query = em.createQuery(CONTAGEM_DE_REGISTROS, Long.class);
			query.setParameter("idAlbum", idAlbum);
			Long count = query.getSingleResult();
			if (count > 0) {
				em.createQuery(EXCLUIR_REGISTROS)
						.setParameter("idAlbum", idAlbum)
						.executeUpdate();
				
			}
			dao.deleteById(idAlbum);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
