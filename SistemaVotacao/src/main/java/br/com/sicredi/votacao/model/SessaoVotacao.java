package br.com.sicredi.votacao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.sicredi.votacao.enums.StatusSessaoVotacaoEnum;
import br.com.sicredi.votacao.enums.VotoEnum;

@Entity
public class SessaoVotacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private Pauta pauta;

	@Column
	@Enumerated(EnumType.ORDINAL)
	private StatusSessaoVotacaoEnum statusSessaoVotacao;

	@OneToMany(mappedBy = "sesaoVotacao", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VotacaoAssociado> votacaoAssociado;

	@Column
	private String descricao;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the pauta
	 */
	public Pauta getPauta() {
		return pauta;
	}

	/**
	 * @param pauta the pauta to set
	 */
	public void setPauta(Pauta pauta) {
		this.pauta = pauta;
	}

	/**
	 * @return the statusSessaoVotacao
	 */
	public StatusSessaoVotacaoEnum getStatusSessaoVotacao() {
		return statusSessaoVotacao;
	}

	/**
	 * @param statusSessaoVotacao the statusSessaoVotacao to set
	 */
	public void setStatusSessaoVotacao(StatusSessaoVotacaoEnum statusSessaoVotacao) {
		this.statusSessaoVotacao = statusSessaoVotacao;
	}

	/**
	 * @return the votacaoAssociado
	 */
	public List<VotacaoAssociado> getVotacaoAssociado() {
		if (votacaoAssociado == null) {
			votacaoAssociado = new ArrayList<VotacaoAssociado>();
		}
		return votacaoAssociado;
	}

	public void addVotacaoAssociado(Associado associado, VotoEnum voto) {

		if (this.votacaoAssociado == null) {
			this.votacaoAssociado = new ArrayList<VotacaoAssociado>();
		}

		VotacaoAssociado votacaoAssociado = new VotacaoAssociado(this, associado, voto);

		System.out.println(this);
		System.out.println(associado);
		System.out.println(voto);
		this.votacaoAssociado.add(votacaoAssociado);

	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SessaoVotacao)) {
			return false;
		}
		SessaoVotacao other = (SessaoVotacao) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
