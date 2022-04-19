package com.unisinos.enginepetry;

import static com.unisinos.enginepetry.model.TipoConexaoEnum.CONSUMO;
import static com.unisinos.enginepetry.model.TipoConexaoEnum.GERACAO;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.unisinos.enginepetry.model.Lugar;
import com.unisinos.enginepetry.model.RedePetry;
import com.unisinos.enginepetry.model.TipoConexaoEnum;
import com.unisinos.enginepetry.model.Transicao;
import com.unisinos.enginepetry.model.Util;

@SpringBootApplication
public class EnginePetryApplication {

	public RedePetry carregarRedeDeArquivo(String pathFile) {
		File fileIn = new File(pathFile);
		try {
			RedePetry redePetry = new RedePetry();
			List<String> lines = Util.loadLinesBuffer(fileIn, "UTF-8");
			for (String line : lines) {
				String[] atts = line.split("-");
				if (atts[0].length() < 1) {
					throw new RuntimeException();
				}
				char val = atts[0].charAt(0);
				switch (val) {
				case 'L':
					redePetry.adicionarLugar(new Lugar(atts[0]));
					break;
				case 'T':
					redePetry.adicionarTransicao(new Transicao(atts[0]));
					break;
				case 'A':
					if (atts.length != 5) {
						throw new RuntimeException();
					}
					TipoConexaoEnum tipo = TipoConexaoEnum.byPrefixe(atts[1]);
					redePetry.adicionarConexao(Integer.parseInt(atts[4]), tipo, (tipo == CONSUMO ? atts[2] : atts[3]), (tipo == CONSUMO ? atts[3] : atts[2]));
					break;
				case 'M':
					if (atts.length != 3) {
						throw new RuntimeException();
					}
					redePetry.adicionarTokens(atts[2], Integer.parseInt(atts[1]));
					break;
				default:
					throw new RuntimeException();
				}
			}
			return redePetry;
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	public static void main(String[] args) {
//		var redePetry = new RedePetry();
//		// L 1 1
//		redePetry.adicionarLugar(new Lugar("1", 1));
//		// T 1
//		redePetry.adicionarTransicao(new Transicao("1"));
//		// L 2 0
//		redePetry.adicionarLugar(new Lugar("2", 0));
//		// C C L1 T1
//		redePetry.adicionarConexao(1, CONSUMO, "L1", "T1");
//		// C G T1 L2
//		redePetry.adicionarConexao(1, GERACAO, "L2", "T1");
//
//		System.out.println(redePetry.getRedeString());
//
//		redePetry.executaTransicao("T1");
//
//		System.out.println(redePetry.getRedeString());

		RedePetry rede = new EnginePetryApplication().carregarRedeDeArquivo("C:\\_DEV\\_CCF\\engine-petry\\src\\main\\resources\\Entrada\\RedePetry.txt");

		System.out.println(rede.getRedeString());
	}

	/**
	 * -> Definição de Padrão do arquivo -> Leitura do Arquivo (colocar marcas) ->
	 * Montagem da Rede - OK -> Scanner da rede -> Execução do ciclo (executa as
	 * transições ativas no scanner da rede) -> Resolução da concorrência ->
	 * 
	 * 
	 * 
	 */

}
