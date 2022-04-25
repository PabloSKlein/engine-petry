package com.unisinos.enginepetry;

import static com.unisinos.enginepetry.model.TipoConexaoEnum.CONSUMO;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.unisinos.enginepetry.model.Lugar;
import com.unisinos.enginepetry.model.RedePetry;
import com.unisinos.enginepetry.model.TipoConexaoEnum;
import com.unisinos.enginepetry.model.Transicao;
import com.unisinos.enginepetry.model.Util;

@SpringBootApplication
public class EnginepetryApplication {

	public static RedePetry rede;

	public static RedePetry carregarRedeDeArquivo(String pathFile) {
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
		rede = carregarRedeDeArquivo("C:\\_DEV\\_CCF\\engine-petry\\src\\main\\resources\\Entrada\\RedePetry.txt");
		SpringApplication.run(EnginepetryApplication.class, args);
	}

}
