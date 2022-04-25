package com.unisinos.enginepetry.model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.unisinos.enginepetry.model.TipoConexaoEnum.CONSUMO;
import static com.unisinos.enginepetry.model.TipoConexaoEnum.GERACAO;
import static java.util.Collections.shuffle;

public class RedePetry {
    private int cicloAtual = 0;
    private final List<Lugar> lugares = new ArrayList<>();
    private final List<Conexao> conexoes = new ArrayList<>();
    private final List<Transicao> transicoes = new ArrayList<>();
    JTextArea textArea;

//	private class MKeyListener extends KeyAdapter {
//
//		@Override
//		public void keyPressed(KeyEvent event) {
//			if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
//				System.exit(0);
//			}
//
//			if (event.getKeyCode() == KeyEvent.VK_ENTER) {
//
//				executaCiclo();
//				textArea.append("\n\n" + getRedeString() + "\n\n ENTER : Novo Ciclo \n ESC : Sair");
//			}
//		}
//	}

//	public void start() {
//		textArea = new JTextArea();
//
//		textArea.addKeyListener(new MKeyListener());
//		textArea.setEditable(false);
//
//		textArea.setText(getRedeString() + "\n\n ENTER : Novo Ciclo \n ESC : Sair");
//
//		JFrame jframe = new JFrame();
//
//		jframe.add(textArea);
//
//		jframe.setSize(800, 600);
//		jframe.revalidate();
//		jframe.repaint();
//
//		jframe.setVisible(true);
//
//	}

    public void adicionarLugar(Lugar lugar) {
        if (buscaLugar(lugar.getId()) != null) {
            throw new RuntimeException();
        }
        lugares.add(lugar);
    }

    public void adicionarTransicao(Transicao transicao) {
        if (buscaTransicao(transicao.getId()) != null) {
            throw new RuntimeException();
        }
        transicoes.add(transicao);
    }

    public void adicionarTokens(String idLugar, int quantidadeTokens) {
        buscaLugar(idLugar).adicionaTokens(quantidadeTokens);
    }

    public void adicionarConexao(int valor, TipoConexaoEnum tipoConexao, String idLugar, String idTransicao) {
        if (buscaConexao(idLugar, idTransicao, tipoConexao)) {
            throw new RuntimeException();
        }
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

    public List<Conexao> getConexoesPossiveis() {
        return conexoes.stream().filter(conexao -> conexao.getTipoConexao() == CONSUMO && conexao.getPeso() <= conexao.getOrigem().getTokens()).collect(Collectors.toList());
    }

    private Transicao buscaTransicao(String idTransicao) {
        return transicoes.stream().filter(lugar -> lugar.getId().equals(idTransicao)).findAny().orElse(null);
    }

    private Lugar buscaLugar(String idLugar) {
        return lugares.stream().filter(lugar -> lugar.getId().equals(idLugar)).findAny().orElse(null);
    }

    private boolean buscaConexao(String idLugar, String idTransicao, TipoConexaoEnum tipo) {
        return conexoes.stream().anyMatch(c -> c.getlugar().getId().equals(idLugar) && c.getTransicao().getId().equals(idTransicao) && c.getTipoConexao() == tipo);
    }

    public String getRedeString() {
        return "Lugares: " + lugares.stream().map(it -> it.getId() + " Tokens:" + it.getTokens()).collect(Collectors.joining(";")) + "\n Transicoes: "
                + transicoes.stream().map(Transicao::getId).collect(Collectors.joining(";")) + "\n Conexoes: "
                + conexoes.stream().map(Conexao::getId).collect(Collectors.joining(";"));
    }

    public void executaTransicao(String idTransicao) {
        var conexoesDaTransicao = Optional.ofNullable(buscaTransicao(idTransicao))
                .map(Transicionavel::getConexoes)
                .orElseGet(List::of);

        if (todasConexoesDeConsumoPossuemOsTokensNecessarios(conexoesDaTransicao)) {
            System.out.println("TRANSIÇÃO EXECUTADA:" + idTransicao);
            consomeTokens(conexoesDaTransicao);
            geraTokens(conexoesDaTransicao);
        } else {
            System.out.println("TRANSIÇÃO NÃO EXECUTADA:" + idTransicao);
        }
    }

    private void consomeTokens(List<Conexao> conexoes) {
        conexoes.stream().filter(it -> it.getTipoConexao() == CONSUMO).forEach(it -> it.getlugar().removeTokens(it.getPeso()));
    }

    private void geraTokens(List<Conexao> conexoes) {
        conexoes.stream().filter(it -> it.getTipoConexao() == GERACAO).forEach(it -> it.getlugar().adicionaTokens(it.getPeso()));
    }

    private boolean todasConexoesDeConsumoPossuemOsTokensNecessarios(List<Conexao> conexoes) {
        return conexoes.stream().filter(it -> it.getTipoConexao() == CONSUMO).allMatch(it -> it.getPeso() <= it.getOrigem().getTokens());
    }

    public void executaCiclo() {
        var ativas = buscaTransicoesAtivas();
        shuffle(ativas);

        for (Transicao transicao : ativas) {
            System.out.println("ATIVA:" + transicao.getId());
            executaTransicao(transicao.getId());
        }
        System.out.println("Ciclo:" + cicloAtual);
        cicloAtual++;
    }

    private List<Transicao> buscaTransicoesAtivas() {
        List<Transicao> resultado = new ArrayList<>();
        for (Transicao transicao : transicoes) {
            if (todasConexoesDeConsumoPossuemOsTokensNecessarios(transicao.getConexoes())) {
                resultado.add(transicao);
            }
        }
        return resultado;
    }

    public List<Lugar> buscaLugaresEmOrdemAlfabetica(){
        return lugares.stream().sorted(Comparator.comparing(Lugar::getId)).collect(Collectors.toList());
    }

    public List<Transicao> buscaTransicaoEmOrdemAlfabetica(){
        return transicoes.stream().sorted(Comparator.comparing(Transicao::getId)).collect(Collectors.toList());
    }
}
