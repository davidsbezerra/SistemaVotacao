package br.com.sicredi.votacao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sicredi.votacao.DAO.VotacaoAssociadoDAO;
import br.com.sicredi.votacao.enums.StatusSessaoVotacaoEnum;
import br.com.sicredi.votacao.enums.VotoEnum;
import br.com.sicredi.votacao.exception.NegocioException;
import br.com.sicredi.votacao.model.Associado;
import br.com.sicredi.votacao.model.Pauta;
import br.com.sicredi.votacao.model.VotacaoAssociado;
import br.com.sicredi.votacao.repository.VotacaoAssociadoRepository;
import br.com.sicredi.votacao.to.VotacaoAssociadoTO;

@Service
public class VotacaoAssociadoService {

	@Autowired
	private VotacaoAssociadoRepository associadoService;

	@Autowired
	private VotacaoAssociadoDAO votacaoAssociadoDAO;

	public void adicionarVotacao(Pauta pauta, Associado associado, VotoEnum voto) throws NegocioException {

		if (pauta.getSessaoVotacao() == null
				|| StatusSessaoVotacaoEnum.ABERTA != pauta.getSessaoVotacao().getStatusSessaoVotacao()) {

			throw new NegocioException("A sessão de votação não está aberta");

		}

		if (votacaoAssociadoDAO.verificarSeAssociadoJaVotou(pauta.getSessaoVotacao(), associado)) {

			throw new NegocioException("O associado já efetuou o voto nesta pauta");

		}

		VotacaoAssociado votacaoAssociado = new VotacaoAssociado(pauta.getSessaoVotacao(), associado, voto);
		
		associadoService.save(votacaoAssociado);

	}

	public List<VotacaoAssociadoTO> contabilizarVotos(Pauta pauta) throws NegocioException {

		if (pauta != null && pauta.getSessaoVotacao() != null
				&& StatusSessaoVotacaoEnum.FECHADA != pauta.getSessaoVotacao().getStatusSessaoVotacao()) {

			throw new NegocioException("A sessão de votação não está fechada");

		}

		return votacaoAssociadoDAO.resultadoVotacao(pauta);

	}

	public List<VotacaoAssociadoTO> contabilizarVotos() throws NegocioException {

		return votacaoAssociadoDAO.resultadoVotacao(null);

	}

}
