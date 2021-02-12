package br.com.sicredi.votacao.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sicredi.votacao.model.Associado;
import br.com.sicredi.votacao.repository.AssociadoRepository;
import br.com.sicredi.votacao.service.AssociadoService;

@RestController
@RequestMapping("/associado")
public class AssociadoController {

	@Autowired
	private AssociadoService associadoService;

	@Autowired
	private AssociadoRepository associadoRepository;

	@PostMapping("/adicionar")
	public Associado adicionar(@Valid @RequestBody Associado associado) {
		return associadoService.salvar(associado);
	}

	@PutMapping("/atualizar")
	public Associado atualizar(@Valid @RequestBody Associado associado) {
		return associadoService.salvar(associado);
	}

	@DeleteMapping("/deletar")
	public ResponseEntity<Void> deletar(@RequestParam Long idAssociado) {

		Optional<Associado> optionalPauta = associadoRepository.findById(idAssociado);

		if (optionalPauta.isPresent()) {

			associadoService.excluir(idAssociado);

			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

}
