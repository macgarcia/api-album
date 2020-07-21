package com.macgarcia.album.resources;

import java.util.Optional;

import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.macgarcia.album.dto.saida.FotoDtoSaida;
import com.macgarcia.album.entity.Foto;
import com.macgarcia.album.services.FotoService;

@RestController
@RequestMapping(value = "/fotos")
@CrossOrigin
public class FotoResource {

	private FotoService service;

	@Autowired
	public FotoResource(FotoService service) {
		this.service = service;
	}

	@PostMapping(value = "/novaFoto/{descricao}/{idAlbum}")
	public ResponseEntity<?> salvarFoto(@PathVariable("descricao") String descricao, 
										@PathVariable("idAlbum") Long idAlbum,
										@RequestBody Part arquivo) {
		Optional<Foto> novaFoto = service.montarFoto(descricao, idAlbum, arquivo);
		if (!novaFoto.isPresent()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao montar os dados");
		}
		boolean salvou = service.salvar(novaFoto.get());
		if (!salvou) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar salvar os dados.");
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping(value = "/{idFoto}")
	public ResponseEntity<?> excluirFoto(@PathVariable("idFoto") Long idFoto) {
		boolean existe = service.verificarExistencia(idFoto);
		if (!existe) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dados inexistentes.");
		}
		boolean excluiu = service.excluirFoto(idFoto);
		if (!excluiu) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar excluir os dados.");
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping(value = "/{idAlbum}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> buscarFotosDoAlbum(@PathVariable("idAlbum") Long idAlbum, 
												@PageableDefault(page = 0, size = 6) Pageable pageble) {
		Page<FotoDtoSaida> fotos = service.buscarFotos(idAlbum, pageble);
		return ResponseEntity.status(HttpStatus.OK).body(fotos);
	}
	
	@GetMapping(value = "/unica/{idFoto}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> buscarUnicaFoto(@PathVariable("idFoto") Long idFoto) {
		boolean existe = service.verificarExistencia(idFoto);
		if (!existe) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dados inexistentes.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(service.buscarFoto(idFoto));
	}

}
