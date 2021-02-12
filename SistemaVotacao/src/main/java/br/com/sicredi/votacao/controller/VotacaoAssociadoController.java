package br.com.sicredi.votacao.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.sicredi.votacao.enums.VotoEnum;
import br.com.sicredi.votacao.exception.NegocioException;
import br.com.sicredi.votacao.model.Associado;
import br.com.sicredi.votacao.model.Pauta;
import br.com.sicredi.votacao.repository.AssociadoRepository;
import br.com.sicredi.votacao.repository.PautaRepository;
import br.com.sicredi.votacao.service.PautaService;
import br.com.sicredi.votacao.service.VotacaoAssociadoService;
import br.com.sicredi.votacao.to.HabilitacaoVotacaoTO;
import br.com.sicredi.votacao.to.VotacaoAssociadoTO;

@RestController
@RequestMapping("/votacao")
public class VotacaoAssociadoController {

	@Autowired
	private PautaRepository pautaRepository;

	@Autowired
	private AssociadoRepository associadoRepository;

	@Autowired
	private PautaService pautaService;

	@Autowired
	private VotacaoAssociadoService votacaoAssociadoService;

	@GetMapping("/resultado-votacao")
	public ResponseEntity<List<VotacaoAssociadoTO>> resultadoVotacao(@RequestParam(required = false) Long idPauta) {

		try {

			if (idPauta == null) {
				return ResponseEntity.ok(votacaoAssociadoService.contabilizarVotos());
			}

			Optional<Pauta> optionalPauta = pautaRepository.findById(idPauta);

			if (optionalPauta.isPresent()) {
				Pauta pauta = optionalPauta.get();

				return ResponseEntity.ok(votacaoAssociadoService.contabilizarVotos(pauta));
			}
		} catch (NegocioException e) {

			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/adicionar-voto")
	public ResponseEntity<?> adicionar(@RequestParam Long idPauta, @RequestParam Long idAssociado, VotoEnum voto) {

		Optional<Pauta> optionalPauta = pautaRepository.findById(idPauta);
		Optional<Associado> optionalAssociado = associadoRepository.findById(idAssociado);

		try {

			if (optionalPauta.isPresent() && optionalAssociado.isPresent()) {
				Pauta pauta = optionalPauta.get();
				Associado associado = optionalAssociado.get();

				votacaoAssociadoService.adicionarVotacao(pauta, associado, voto);

				return ResponseEntity.ok().build();
			}
		} catch (NegocioException e) {

			System.out.println(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.notFound().build();

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

	@GetMapping("/votar-com-habilitacao")
	public ResponseEntity<?> adicionarVotoComHabilitacao(@RequestParam Long idPauta, @RequestParam Long idAssociado,
			VotoEnum voto) {

		Optional<Pauta> optionalPauta = pautaRepository.findById(idPauta);
		Optional<Associado> optionalAssociado = associadoRepository.findById(idAssociado);

		try {

			if (optionalPauta.isPresent() && optionalAssociado.isPresent()) {
				Pauta pauta = optionalPauta.get();
				Associado associado = optionalAssociado.get();

				RestTemplate restTemplate = new RestTemplate();

				String url = "https://user-info.herokuapp.com/users/" + associado.getCpf();
				System.out.println(url);

				ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

				if (response.getStatusCode() == HttpStatus.OK) {

					ObjectMapper objectMapper = new ObjectMapper();

					HabilitacaoVotacaoTO habilitacao = objectMapper.readValue(response.getBody(),
							HabilitacaoVotacaoTO.class);

					if (!habilitacao.getStatus().equals(HabilitacaoVotacaoTO.HABILITADO)) {
						System.out.println("CPF não autorizado");
						return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
					}

				}

				votacaoAssociadoService.adicionarVotacao(pauta, associado, voto);

				return ResponseEntity.ok().build();
			}
		} catch (NegocioException e) {

			System.out.println(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		} catch (IOException e) {

			System.out.println(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		return ResponseEntity.notFound().build();

	}

}
