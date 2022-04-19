package com.unisinos.enginepetry.model;

import static com.unisinos.enginepetry.model.ComponentePetryEnum.LUGAR;

public class Lugar extends Transicionavel {

	private final String id;
	private int tokens;

	public Lugar(String id, int tokens) {
		this.id = id;
		this.tokens = tokens;
	}

	public Lugar(String id) {
		this.id = id;
		this.tokens = 0;
	}

	@Override
	public ComponentePetryEnum getTipoComponente() {
		return LUGAR;
	}

	public int getTokens() {
		return this.tokens;
	}

	public void removeTokens(int valor) {
		if (valor > tokens)
			throw new RuntimeException();
		tokens -= valor;
	}

	public void adicionaTokens(int valor) {
		if (valor < 0)
			throw new RuntimeException();
		tokens += valor;
	}

	public String getId() {
		return id;
	}
}
