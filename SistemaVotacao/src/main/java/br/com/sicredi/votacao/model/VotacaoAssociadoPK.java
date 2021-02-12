//package br.com.sicredi.votacao.model;
//
//import java.io.Serializable;
//
//import javax.persistence.Column;
//
//public class VotacaoAssociadoPK implements Serializable {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//	@Column
//	private Long sesaoVotacao;
//
//	@Column
//	private Long associado;
//
//	/**
//	 * @return the sesaoVotacao
//	 */
//	public Long getSesaoVotacao() {
//		return sesaoVotacao;
//	}
//
//	/**
//	 * @param sesaoVotacao the sesaoVotacao to set
//	 */
//	public void setSesaoVotacao(Long sesaoVotacao) {
//		this.sesaoVotacao = sesaoVotacao;
//	}
//
//	/**
//	 * @return the associado
//	 */
//	public Long getAssociado() {
//		return associado;
//	}
//
//	/**
//	 * @param associado the associado to set
//	 */
//	public void setAssociado(Long associado) {
//		this.associado = associado;
//	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((associado == null) ? 0 : associado.hashCode());
//		result = prime * result + ((sesaoVotacao == null) ? 0 : sesaoVotacao.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj) {
//			return true;
//		}
//		if (!(obj instanceof VotacaoAssociadoPK)) {
//			return false;
//		}
//		VotacaoAssociadoPK other = (VotacaoAssociadoPK) obj;
//		if (associado == null) {
//			if (other.associado != null) {
//				return false;
//			}
//		} else if (!associado.equals(other.associado)) {
//			return false;
//		}
//		if (sesaoVotacao == null) {
//			if (other.sesaoVotacao != null) {
//				return false;
//			}
//		} else if (!sesaoVotacao.equals(other.sesaoVotacao)) {
//			return false;
//		}
//		return true;
//	}
//
//	
//	
//}
