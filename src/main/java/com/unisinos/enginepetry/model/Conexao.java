package com.unisinos.enginepetry.model;

import static com.unisinos.enginepetry.model.ComponentePetryEnum.CONEXAO;
import static com.unisinos.enginepetry.model.TipoConexaoEnum.CONSUMO;

public class Conexao implements ComponentePetry {
    private static final String PREFIXO = "C";

    private int valor;
    private final TipoConexaoEnum tipoConexao;
    private final Lugar lugar;
    private final Transicao transicao;

    public Conexao(int valor, TipoConexaoEnum tipoConexao, Lugar lugar, Transicao transicao) {
        if (valor < 1) throw new RuntimeException();
        this.valor = valor;
        this.tipoConexao = tipoConexao;
        this.lugar = lugar;
        this.transicao = transicao;
    }

    public Conexao(TipoConexaoEnum tipoConexao, Lugar lugar, Transicao transicao) {
        this.valor = 1;
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
        return PREFIXO + getOrigem().getId() + getDestino().getId();
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public TipoConexaoEnum getTipoConexao() {
        return tipoConexao;
    }

    public Transicionavel getOrigem(){
        return tipoConexao == CONSUMO ? lugar : transicao;
    }

    public Transicionavel getDestino(){
        return tipoConexao == CONSUMO ? transicao : lugar;
    }
}
