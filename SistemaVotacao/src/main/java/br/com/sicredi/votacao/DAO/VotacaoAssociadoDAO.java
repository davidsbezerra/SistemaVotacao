package br.com.sicredi.votacao.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.sicredi.votacao.enums.StatusSessaoVotacaoEnum;
import br.com.sicredi.votacao.model.Associado;
import br.com.sicredi.votacao.model.Pauta;
import br.com.sicredi.votacao.model.SessaoVotacao;
import br.com.sicredi.votacao.model.VotacaoAssociado;
import br.com.sicredi.votacao.to.VotacaoAssociadoTO;

@Repository
public class VotacaoAssociadoDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public List<VotacaoAssociadoTO> resultadoVotacao(Pauta pauta) {

		StringBuilder jpql = new StringBuilder();
		jpql.append(" SELECT ");
		jpql.append("	new br.com.sicredi.votacao.to.VotacaoAssociadoTO( ");
		jpql.append("	pa.id, ");
		jpql.append("	va.voto, ");
		jpql.append("	count(va.voto) ) ");
		jpql.append(" FROM Pauta pa ");
		jpql.append(" LEFT JOIN pa.sessaoVotacao sv ");
		jpql.append(" LEFT JOIN sv.votacaoAssociado va ");
		jpql.append(" WHERE 0 = 0 ");

		Map<String, Object> params = new HashMap<String, Object>();

		if (pauta != null) {
			
			jpql.append("	AND pa = :pauta ");
			params.put("pauta", pauta);
	
			jpql.append("	AND sv.statusSessaoVotacao = :statusVotacao ");
			params.put("statusVotacao", StatusSessaoVotacaoEnum.FECHADA);
		}

		jpql.append(" GROUP BY ");
		jpql.append("	pa.id, ");
		jpql.append("	va.voto ");

		Query query = this.entityManager.createQuery(jpql.toString(), VotacaoAssociadoTO.class);

		params.forEach((chave, valor) -> query.setParameter(chave, valor));

		@SuppressWarnings("unchecked")
		List<VotacaoAssociadoTO> resultado = (List<VotacaoAssociadoTO>) query.getResultList();

		return resultado;

	}

	public Boolean verificarSeAssociadoJaVotou(SessaoVotacao sesaoVotacao, Associado associado) {

		StringBuilder jpql = new StringBuilder();
		jpql.append(" SELECT va ");
		jpql.append(" FROM VotacaoAssociado va ");
		jpql.append(" WHERE 0 = 0 ");

		Map<String, Object> params = new HashMap<String, Object>();

		if (sesaoVotacao != null) {
			jpql.append("	AND va.sesaoVotacao = :sesaoVotacao ");
			params.put("sesaoVotacao", sesaoVotacao);
		}

		if (associado != null) {
			jpql.append("	AND va.associado = :associado ");
			params.put("associado", associado);
		}

		Query query = this.entityManager.createQuery(jpql.toString(), VotacaoAssociado.class);

		params.forEach((chave, valor) -> query.setParameter(chave, valor));

		List<?> resultado =   query.getResultList();

		return !resultado.isEmpty();

	}

}