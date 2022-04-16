package com.unisinos.enginepetry.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.unisinos.enginepetry.model.TipoConexaoEnum.CONSUMO;
import static com.unisinos.enginepetry.model.TipoConexaoEnum.GERACAO;

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
        var lugar = buscaLugar(idLugar);
        var transicao = buscaTransicao(idTransicao);
        var conexao = new Conexao(valor, tipoConexao, lugar, transicao);

        conexoes.add(conexao);
        transicao.adicionaConexao(conexao);
        lugar.adicionaConexao(conexao);
    }

    public void adicionarConexao(TipoConexaoEnum tipoConexao, String idLugar, String idTransicao) {
        adicionarConexao(1, tipoConexao, idLugar, idTransicao);
    }

    ///teste
    public List<Conexao> getConexoesPossiveis() {
        return conexoes.stream()
                .filter(conexao -> conexao.getTipoConexao() == CONSUMO
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
                + "\n Conexoes: " + conexoes.stream().map(Conexao::getId).collect(Collectors.joining(";"));
    }

    public void executaTransicao(String idTransicao) {
        var conexoesDaTransicao = buscaTransicao(idTransicao).getConexoes();

        if (todasConexoesDeConsumoPossuemOsTokensNecessarios(conexoesDaTransicao)) {
            consomeTokens(conexoesDaTransicao);
            geraTokens(conexoesDaTransicao);
        }
    }

    private void consomeTokens(List<Conexao> conexoes) {
        conexoes.stream()
                .filter(it -> it.getTipoConexao() == CONSUMO)
                .forEach(it -> it.getlugar().removeTokens(it.getValor()));
    }

    private void geraTokens(List<Conexao> conexoes) {
        conexoes.stream()
                .filter(it -> it.getTipoConexao() == GERACAO)
                .forEach(it -> it.getlugar().adicionaTokens(it.getValor()));
    }

    private boolean todasConexoesDeConsumoPossuemOsTokensNecessarios(List<Conexao> conexoes) {
        return conexoes.stream()
                .filter(it -> it.getTipoConexao() == CONSUMO)
                .allMatch(it -> it.getValor() <= it.getOrigem().getTokens());
    }
}
