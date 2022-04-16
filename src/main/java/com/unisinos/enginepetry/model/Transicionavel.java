package com.unisinos.enginepetry.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Transicionavel implements ComponentePetry {
    private final List<Conexao> conexoes = new ArrayList<>();

    public abstract int getTokens();

    public List<Conexao> getConexoes() {
        return conexoes;
    }

    public void adicionaConexao(Conexao conexao) {
        getConexoes().add(conexao);
    }
}
