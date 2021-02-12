package br.com.sicredi.votacao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.sicredi.votacao.enums.VotoEnum;

@Entity
public class VotacaoAssociado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private SessaoVotacao sesaoVotacao;

	@ManyToOne
	private Associado associado;

	@Column
	@Enumerated(EnumType.STRING)
	private VotoEnum voto;
	
	public VotacaoAssociado() {
		
	}

	public VotacaoAssociado(SessaoVotacao sesaoVotacao, Associado associado, VotoEnum voto) {
		this.sesaoVotacao = sesaoVotacao;
		this.associado = associado;
		this.voto = voto;
	}

	/**
	 * @return the sesaoVotacao
	 */
	public SessaoVotacao getSesaoVotacao() {
		return sesaoVotacao;
	}

	/**
	 * @return the associado
	 */
	public Associado getAssociado() {
		return associado;
	}

	/**
	 * @param associado the associado to set
	 */
	public void setAssociado(Associado associado) {
		this.associado = associado;
	}

	/**
	 * @return the voto
	 */
	public VotoEnum getVoto() {
		return voto;
	}

	/**
	 * @param voto the voto to set
	 */
	public void setVoto(VotoEnum voto) {
		this.voto = voto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((associado == null) ? 0 : associado.hashCode());
		result = prime * result + ((sesaoVotacao == null) ? 0 : sesaoVotacao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof VotacaoAssociado)) {
			return false;
		}
		VotacaoAssociado other = (VotacaoAssociado) obj;
		if (associado == null) {
			if (other.associado != null) {
				return false;
			}
		} else if (!associado.equals(other.associado)) {
			return false;
		}
		if (sesaoVotacao == null) {
			if (other.sesaoVotacao != null) {
				return false;
			}
		} else if (!sesaoVotacao.equals(other.sesaoVotacao)) {
			return false;
		}
		return true;
	}

}
