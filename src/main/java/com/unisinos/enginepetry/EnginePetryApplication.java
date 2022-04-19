package com.unisinos.enginepetry;

import static com.unisinos.enginepetry.model.TipoConexaoEnum.CONSUMO;
import static com.unisinos.enginepetry.model.TipoConexaoEnum.GERACAO;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.unisinos.enginepetry.model.Lugar;
import com.unisinos.enginepetry.model.RedePetry;
import com.unisinos.enginepetry.model.Transicao;

@SpringBootApplication
public class EnginePetryApplication {

	public static void main(String[] args) {
		var redePetry = new RedePetry();
		// L 1 1
		redePetry.adicionarLugar(new Lugar("1", 1));
		// T 1
		redePetry.adicionarTransicao(new Transicao("1"));
		// L 2 0
		redePetry.adicionarLugar(new Lugar("2", 0));
		// C C L1 T1
		redePetry.adicionarConexao(1, CONSUMO, "L1", "T1");
		// C G L2 T1
		redePetry.adicionarConexao(1, GERACAO, "L2", "T1");
		// L 3 0
		redePetry.adicionarLugar(new Lugar("3", 0));
		// T 2
		redePetry.adicionarTransicao(new Transicao("2"));
		// C C L2 T2
		redePetry.adicionarConexao(1, CONSUMO, "L2", "T2");
		// C G L3 T2
		redePetry.adicionarConexao(2, GERACAO, "L3", "T2");
		// L 4 0
		redePetry.adicionarLugar(new Lugar("4", 0));
//        //T 3
		redePetry.adicionarTransicao(new Transicao("3"));
//        //C C L3 T2
		redePetry.adicionarConexao(2, CONSUMO, "L3", "T3");
//        //C G L4 T3
		redePetry.adicionarConexao(1, GERACAO, "L4", "T3");

		redePetry.start();
		
	}

	/**
	 * -> Definição de Padrão do arquivo -> Leitura do Arquivo (colocar marcas)
	 * -> Montagem da Rede - OK -> Scanner da rede -> Execução do ciclo (executa
	 * as transições ativas no scanner da rede) -> Resolução da concorrência ->
	 * 
	 * 
	 * 
	 */

}
