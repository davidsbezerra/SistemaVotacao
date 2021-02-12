package br.com.sicredi.votacao.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sicredi.votacao.model.Pauta;
import br.com.sicredi.votacao.repository.PautaRepository;
import br.com.sicredi.votacao.service.PautaService;

@RestController
@RequestMapping("/pauta")
public class PautaController {

	@Autowired
	private PautaRepository pautaRepository;

	@Autowired
	private PautaService pautaService;

	@GetMapping
	public List<Pauta> listar() {
		return pautaRepository.findAll();
	}

	@GetMapping("/abrir-votacao")
	public ResponseEntity<Void> abrirVotacao(@RequestParam Long idPauta,
			@RequestParam(required = false) Integer minutos) {

		Optional<Pauta> optionalPauta = pautaRepository.findById(idPauta);

		if (optionalPauta.isPresent()) {
			Pauta pauta = optionalPauta.get();

			pautaService.abrirSessaoVotacao(pauta, minutos);

			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

	@CrossOrigin
	@PostMapping("/adicionar")
	public Pauta adicionar(@Valid @RequestBody Pauta pauta) {
		return pautaService.salvar(pauta);
	}

	@PutMapping("/atualizar")
	public Pauta atualizar(@Valid @RequestBody Pauta pauta) {
		return pautaService.salvar(pauta);
	}

	@DeleteMapping("/deletar")
	public ResponseEntity<Void> deletar(@RequestParam Long idPauta) {

		Optional<Pauta> optionalPauta = pautaRepository.findById(idPauta);

		if (optionalPauta.isPresent()) {

			pautaService.excluir(idPauta);

			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

}
