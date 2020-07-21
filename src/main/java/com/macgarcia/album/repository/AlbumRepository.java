package com.macgarcia.album.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.macgarcia.album.entity.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

	Page<Album> findByIdUsuario(Long idUsuario, Pageable page);

}
