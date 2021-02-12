package br.com.sicredi.votacao.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusSessaoVotacaoEnum {

	ABERTA(0), FECHADA(1);

	private Integer value;

	StatusSessaoVotacaoEnum(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@JsonValue
	public int toValue() {
		return ordinal();
	}

}
