package br.com.sicredi.votacao.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.sicredi.votacao.enums.StatusSessaoVotacaoEnum;
import br.com.sicredi.votacao.model.Pauta;
import br.com.sicredi.votacao.model.SessaoVotacao;
import br.com.sicredi.votacao.repository.PautaRepository;

@Service
public class PautaService {

	public static final Integer TEMPO_MINUTOS_DEFAULT_VOTACAO = 2;

	@Autowired
	private PautaRepository pautaRepository;

	public Pauta salvar(Pauta pauta) {
		return pautaRepository.saveAndFlush(pauta);
	}

	public void excluir(Long idPauta) {
		pautaRepository.deleteById(idPauta);
	}

	@Async
	public void abrirSessaoVotacao(Pauta pauta, Integer minutos) {

		Calendar dataFechamento = Calendar.getInstance();
		dataFechamento.add(Calendar.MINUTE, minutos == null ? TEMPO_MINUTOS_DEFAULT_VOTACAO : minutos);

		SessaoVotacao sessaoVotacao = pauta.getSessaoVotacao() != null ? pauta.getSessaoVotacao() : new SessaoVotacao();
		sessaoVotacao.setStatusSessaoVotacao(StatusSessaoVotacaoEnum.ABERTA);
		pauta.setSessaoVotacao(sessaoVotacao);
		
		pauta = this.salvar(pauta);

		Calendar dataAbertura = Calendar.getInstance();
		long tempoAberturaVotacaoPauta = dataFechamento.getTimeInMillis() - dataAbertura.getTimeInMillis();

		try {
			Thread.sleep(tempoAberturaVotacaoPauta);

		} catch (InterruptedException e) {
			System.out.println("SESSÃO INTERROMPIDA");
			e.printStackTrace();
		} finally {
			System.out.println("Votação encerada");
			fecharSessaoVotacao(pauta);
		}
	}

	public void abrirSessaoVotacao(Pauta pauta) {

		this.abrirSessaoVotacao(pauta, null);

	}

	public void fecharSessaoVotacao(Pauta pauta) {

		SessaoVotacao sessaoVotacao = pauta.getSessaoVotacao() != null ? pauta.getSessaoVotacao() : new SessaoVotacao();
		sessaoVotacao.setStatusSessaoVotacao(StatusSessaoVotacaoEnum.FECHADA);
		pauta.setSessaoVotacao(sessaoVotacao);
		salvar(pauta);

	}

}
