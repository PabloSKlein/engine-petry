package com.unisinos.enginepetry.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RedePetry {
    private final List<Lugar> lugares = new ArrayList<>();
    private final List<Conexao> conexoes = new ArrayList<>();
    private final List<Transicao> transicoes = new ArrayList<>();

    public void adicionarLugar(Lugar lugar) {
        lugares.add(lugar);
    }

    public void adicionarTransicao(Transicao transicao) {
        transicoes.add(transicao);
    }

    public void adicionarConexao(int valor, TipoConexaoEnum tipoConexao, String idLugar, String idTransicao) {
        conexoes.add(new Conexao(valor, tipoConexao, buscaLugar(idLugar), buscaTransicao(idTransicao)));
    }

    public void adicionarConexao(TipoConexaoEnum tipoConexao, String idLugar, String idTransicao) {
        adicionarConexao(1, tipoConexao, idLugar, idTransicao);
    }

    ///teste
    public List<Conexao> getConexoesPossiveis() {
        return conexoes.stream()
                .filter(conexao -> conexao.getTipoConexao() == TipoConexaoEnum.CONSUMO
                         && conexao.getValor() <= conexao.getOrigem().getTokens())
                .collect(Collectors.toList());
    }

    private Transicao buscaTransicao(String idTransicao) {
        return transicoes.stream()
                .filter(lugar -> lugar.getId().equals(idTransicao))
                .findAny()
                .orElseThrow(RuntimeException::new);
    }

    private Lugar buscaLugar(String idLugar) {
        return lugares.stream()
                .filter(lugar -> lugar.getId().equals(idLugar))
                .findAny()
                .orElseThrow(RuntimeException::new);
    }

    public String getRedeString() {
        return "Lugares: " + lugares.stream().map(Lugar::getId).collect(Collectors.joining(";"))
                + "\n Transicoes: " + transicoes.stream().map(Transicao::getId).collect(Collectors.joining(";"))
                + "\n Conexoes: " + conexoes.stream().map(Conexao::getId).collect(Collectors.joining(";")) ;
    }
}
