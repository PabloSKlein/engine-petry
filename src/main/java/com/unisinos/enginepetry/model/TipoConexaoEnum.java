package com.unisinos.enginepetry.model;

public enum TipoConexaoEnum {
	CONSUMO, GERACAO;

	public static TipoConexaoEnum byPrefixe(String prefixe) {
		if (prefixe.equals("C")) {
			return CONSUMO;
		} else if (prefixe.equals("G")) {
			return GERACAO;
		}
		throw new RuntimeException();
	}
}
