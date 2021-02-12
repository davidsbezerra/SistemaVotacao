package br.com.sicredi.votacao.to;

public class HabilitacaoVotacaoTO {

	public final static String HABILITADO = "ABLE_TO_VOTE";
	public final static String NAO_HABILITADO = "UNABLE_TO_VOTE";
	private String status;

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
