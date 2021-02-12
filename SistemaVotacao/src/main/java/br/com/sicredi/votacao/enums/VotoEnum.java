package br.com.sicredi.votacao.enums;

public enum VotoEnum {

	SIM("Sim"), NAO("Não");

	private String value;

	VotoEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


}
