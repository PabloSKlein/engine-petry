package com.unisinos.enginepetry.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RedePetry {
	private int cicloAtual = 0;
	private final List<Lugar> lugares = new ArrayList<>();
	private final List<Conexao> conexoes = new ArrayList<>();
	private final List<Transicao> transicoes = new ArrayList<>();
	private StringBuilder log;

	public StringBuilder getLog() {
		if (this.log == null) {
			log = new StringBuilder();
			String lugares = "";
			for (Lugar lugar : buscaLugaresEmOrdemAlfabetica()) {
				lugares += lugar.getId() + " \t";
			}
			String trans = "";
			for (Transicao tran : buscaTransicaoEmOrdemAlfabetica()) {
				trans += tran.getId() + " \t";
			}
			this.log.append("\t" + lugares + trans + "\n");
			criarLinhaLog();
		}
		return log;
	}

	private void criarLinhaLog() {
		String lugares = "";
		for (Lugar lugar : buscaLugaresEmOrdemAlfabetica()) {
			lugares += (lugar.getTokens() == 0 ? "-" : lugar.getTokens()) + " \t";
		}
		String trans = "";
		for (Transicao tran : buscaTransicaoEmOrdemAlfabetica()) {
			boolean ativa = todasConexoesDeConsumoPossuemOsTokensNecessarios(tran.getConexoes());
			trans += (ativa ? "S" : "N") + " \t";
		}
		getLog().append(cicloAtual + "\t" + lugares + trans + "\n");
	}

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
		return conexoes.stream().filter(conexao -> conexao.getTipoConexao() == TipoConexaoEnum.CONSUMO && conexao.getPeso() <= conexao.getOrigem().getTokens())
				.collect(Collectors.toList());
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
		var conexoesDaTransicao = buscaTransicao(idTransicao).getConexoes();

		if (todasConexoesDeConsumoPossuemOsTokensNecessarios(conexoesDaTransicao)) {
			System.out.println("TRANSIÇÃO EXECUTADA:" + idTransicao);
			consomeTokens(conexoesDaTransicao);
			geraTokens(conexoesDaTransicao);
		} else {
			System.out.println("TRANSIÇÃO NÃO EXECUTADA:" + idTransicao);
		}
	}

	private void consomeTokens(List<Conexao> conexoes) {
		conexoes.stream().filter(it -> it.getTipoConexao() == TipoConexaoEnum.CONSUMO).forEach(it -> it.getlugar().removeTokens(it.getPeso()));
	}

	private void geraTokens(List<Conexao> conexoes) {
		conexoes.stream().filter(it -> it.getTipoConexao() == TipoConexaoEnum.GERACAO).forEach(it -> it.getlugar().adicionaTokens(it.getPeso()));
	}

	private boolean todasConexoesDeConsumoPossuemOsTokensNecessarios(List<Conexao> conexoes) {
		return conexoes.stream().filter(it -> it.getTipoConexao() == TipoConexaoEnum.CONSUMO).allMatch(it -> it.getPeso() <= it.getOrigem().getTokens());
	}

	public String executaCiclo() {
		List<Transicao> ativas = buscaTransicoesAtivas();
		for (Transicao transicao : ativas) {
			System.out.println("ATIVA:" + transicao.getId());
			executaTransicao(transicao.getId());
		}
		criarLinhaLog();
		System.out.println("Ciclo:" + cicloAtual);
		cicloAtual++;
		return this.log.toString();
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

	public List<Lugar> buscaLugaresEmOrdemAlfabetica() {
		return lugares.stream().sorted(Comparator.comparing(Lugar::getId)).collect(Collectors.toList());
	}

	public List<Transicao> buscaTransicaoEmOrdemAlfabetica() {
		return transicoes.stream().sorted(Comparator.comparing(Transicao::getId)).collect(Collectors.toList());
	}
}
