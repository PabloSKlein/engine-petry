package com.unisinos.enginepetry.model;

import static com.unisinos.enginepetry.model.ComponentePetryEnum.LUGAR;

public class Lugar extends Transicionavel {
    private static final String PREFIXO = "L";

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

    public void removeTokens(int toRemove) {
        if (toRemove > tokens) throw new RuntimeException();
        tokens -= toRemove;
    }

    public String getId() {
        return PREFIXO + id;
    }
}
