package com.macgarcia.album.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.macgarcia.album.entity.Foto;

public interface FotoRepository extends JpaRepository<Foto, Long> {
	
	@Query("select a from Foto a where a.album.id = :idAlbum")
	Page<Foto> findByIdAlbum(@Param("idAlbum") Long idAlbum, Pageable page);
	
	@Query("select a from Foto a where a.id = :idFoto")
	Foto buscarUnicaFoto(@Param("idFoto") Long idFoto);

}
