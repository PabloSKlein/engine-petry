package com.unisinos.enginepetry.model;

import static com.unisinos.enginepetry.model.ComponentePetryEnum.TRANSICAO;

public class Transicao extends Transicionavel {
	private final String id;

	public Transicao(String id) {
		this.id = id;
	}

	@Override
	public ComponentePetryEnum getTipoComponente() {
		return TRANSICAO;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public int getTokens() {
		return 0;
	}
}
