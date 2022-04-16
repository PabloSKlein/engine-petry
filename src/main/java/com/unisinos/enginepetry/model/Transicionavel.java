package com.unisinos.enginepetry.model;

import java.util.List;

public abstract class Transicionavel implements ComponentePetry {
    private List<Conexao> conexoes;

    public List<Conexao> getConexoes() {
        return conexoes;
    }

    public abstract int getTokens();
}
