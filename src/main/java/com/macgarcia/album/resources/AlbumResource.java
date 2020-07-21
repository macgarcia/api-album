package com.macgarcia.album.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macgarcia.album.dto.entrada.AlbumDtoEntrada;
import com.macgarcia.album.services.AlbumService;

@RestController
@RequestMapping(value = "/albuns")
@CrossOrigin
public class AlbumResource {
	
	private AlbumService service;
	
	@Autowired
	public AlbumResource(AlbumService service) {
		this.service = service;
	}

	@GetMapping(value = "/{idUsuario}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> buscarAlbunsDoUsuario(@PathVariable("idUsuario") Long idUsuario, 
												   @PageableDefault(page = 0, size = 10) Pageable page) {
		if (idUsuario <= 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Identificador inválido");
		}
		var albuns = service.buscarAlbuns(idUsuario, page);
		return ResponseEntity.status(HttpStatus.OK).body(albuns);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> salvarNovoAlbum(@RequestBody AlbumDtoEntrada dto) {
		boolean validou = service.validar(dto);
		if (!validou) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(service.getMensagemDeErro());
		}
		boolean salvou = service.salvar(dto);
		if (!salvou) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar salvar");
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping(value = "/{idAlbum}")
	public ResponseEntity<?> excluirAlbum(@PathVariable("idAlbum") Long idAlbum) {
		boolean existe = service.verificarExistencia(idAlbum);
		if (!existe) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dados inexistentes");
		}
		boolean excluiu = service.excluirAlbum(idAlbum);
		if (!excluiu) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar excluir os dados.");
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
