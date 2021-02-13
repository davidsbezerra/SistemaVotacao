package br.com.sicredi.votacao.testes;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.sicredi.votacao.DAO.VotacaoAssociadoDAO;
import br.com.sicredi.votacao.enums.StatusSessaoVotacaoEnum;
import br.com.sicredi.votacao.enums.VotoEnum;
import br.com.sicredi.votacao.exception.NegocioException;
import br.com.sicredi.votacao.model.Associado;
import br.com.sicredi.votacao.model.Pauta;
import br.com.sicredi.votacao.model.SessaoVotacao;
import br.com.sicredi.votacao.repository.PautaRepository;
import br.com.sicredi.votacao.service.AssociadoService;
import br.com.sicredi.votacao.service.PautaService;
import br.com.sicredi.votacao.service.VotacaoAssociadoService;
import br.com.sicredi.votacao.to.VotacaoAssociadoTO;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TesteVotacaoAssociado {

	@Autowired
	private VotacaoAssociadoService votacaoAssociadoService;

	@Autowired
	private PautaService pautaService;

	@Autowired
	private AssociadoService associadoService;

	@Autowired
	private PautaRepository pautaRepository;

	@Autowired
	private VotacaoAssociadoDAO votacaoAssociadoDAO;

	@Test
	public void adicionarVotacaoSemAbrirSessao() {

		Pauta pauta = new Pauta();
		pauta.setDescricao("Desrição da Pauta");
		pauta = pautaService.salvar(pauta);

		Associado associado = new Associado();
		associado.setNome("Nome");
		associado.setCpf("12345678909");
		associado = associadoService.salvar(associado);

		try {

			votacaoAssociadoService.adicionarVotacao(pauta, associado, VotoEnum.SIM);

		} catch (NegocioException e) {
			assertTrue(true);
		}

	}

	@Test
	public void adicionarSimVotacaoComAberturaSessao() {
		VotoEnum votoTeste = VotoEnum.SIM;

		Pauta pauta = new Pauta();
		pauta.setDescricao("Desrição da Pauta");

		pauta = pautaService.salvar(pauta);

		long qtVotosIni = 0L;

		List<VotacaoAssociadoTO> resultado = votacaoAssociadoDAO.resultadoVotacao(pauta);

		for (VotacaoAssociadoTO votacaoAssociadoTO : resultado) {

			if (votoTeste == votacaoAssociadoTO.getVoto()) {
				qtVotosIni = votacaoAssociadoTO.getQuantidade();
				break;
			}

		}

		SessaoVotacao sessaoVotacao = new SessaoVotacao();
		sessaoVotacao.setStatusSessaoVotacao(StatusSessaoVotacaoEnum.ABERTA);
		pauta.setSessaoVotacao(sessaoVotacao);

		pauta = pautaService.salvar(pauta);

		Associado associado = new Associado();
		associado.setNome("Nome");
		associado.setCpf("12345678909");

		associado = associadoService.salvar(associado);

		pauta = pautaRepository.findById(pauta.getId()).get();

		try {

			votacaoAssociadoService.adicionarVotacao(pauta, associado, votoTeste);

		} catch (NegocioException e) {
			assertTrue(false);
		}
		pautaService.fecharSessaoVotacao(pauta);

		pauta = pautaRepository.findById(pauta.getId()).get();

		long qtVotosFinal = 0L;

		resultado = votacaoAssociadoDAO.resultadoVotacao(pauta);

		for (VotacaoAssociadoTO votacaoAssociadoTO : resultado) {

			if (votoTeste == votacaoAssociadoTO.getVoto()) {
				qtVotosFinal = votacaoAssociadoTO.getQuantidade();
				break;
			}

		}

		assertTrue(qtVotosIni < qtVotosFinal);

	}

}
