package br.com.sicredi.votacao.to;

import br.com.sicredi.votacao.enums.VotoEnum;

public class VotacaoAssociadoTO {

	private Long idPauta;
	private VotoEnum voto;
	private Long quantidade;

	public VotacaoAssociadoTO() {

	}

	public VotacaoAssociadoTO(Long idPauta, VotoEnum voto, Long quantidade) {
		this.idPauta = idPauta;
		this.voto = voto;
		this.quantidade = quantidade;

	}

	/**
	 * @return the idPauta
	 */
	public Long getIdPauta() {
		return idPauta;
	}

	/**
	 * @param idPauta the idPauta to set
	 */
	public void setIdPauta(Long idPauta) {
		this.idPauta = idPauta;
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

	/**
	 * @return the quantidade
	 */
	public Long getQuantidade() {
		return quantidade;
	}

	/**
	 * @param quantidade the quantidade to set
	 */
	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPauta == null) ? 0 : idPauta.hashCode());
		result = prime * result + ((quantidade == null) ? 0 : quantidade.hashCode());
		result = prime * result + ((voto == null) ? 0 : voto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof VotacaoAssociadoTO)) {
			return false;
		}
		VotacaoAssociadoTO other = (VotacaoAssociadoTO) obj;
		if (idPauta == null) {
			if (other.idPauta != null) {
				return false;
			}
		} else if (!idPauta.equals(other.idPauta)) {
			return false;
		}
		if (quantidade == null) {
			if (other.quantidade != null) {
				return false;
			}
		} else if (!quantidade.equals(other.quantidade)) {
			return false;
		}
		if (voto != other.voto) {
			return false;
		}
		return true;
	}

}
