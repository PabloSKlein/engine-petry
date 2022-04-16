package com.unisinos.enginepetry.model;

import static com.unisinos.enginepetry.model.ComponentePetryEnum.CONEXAO;
import static com.unisinos.enginepetry.model.TipoConexaoEnum.CONSUMO;

public class Conexao implements ComponentePetry {
    private static final String PREFIXO = "C";

    private int peso;
    private final TipoConexaoEnum tipoConexao;
    private final Lugar lugar;
    private final Transicao transicao;

    public Conexao(int peso, TipoConexaoEnum tipoConexao, Lugar lugar, Transicao transicao) {
        if (peso < 1) throw new RuntimeException();
        this.peso = peso;
        this.tipoConexao = tipoConexao;
        this.lugar = lugar;
        this.transicao = transicao;
    }

    public Conexao(TipoConexaoEnum tipoConexao, Lugar lugar, Transicao transicao) {
        this.peso = 1;
        this.tipoConexao = tipoConexao;
        this.lugar = lugar;
        this.transicao = transicao;
    }

    @Override
    public ComponentePetryEnum getTipoComponente() {
        return CONEXAO;
    }

    @Override
    public String getId() {
        return PREFIXO + " -> ORIGEM:" + getOrigem().getId() + " DESTINO:" + getDestino().getId() + " VALOR:" + getPeso();
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public TipoConexaoEnum getTipoConexao() {
        return tipoConexao;
    }

    public Transicionavel getOrigem() {
        return tipoConexao == CONSUMO ? lugar : transicao;
    }

    public Transicionavel getDestino() {
        return tipoConexao == CONSUMO ? transicao : lugar;
    }

    public Lugar getlugar() {
        return lugar;
    }

    public Transicao getTransicao() {
        return transicao;
    }
}
