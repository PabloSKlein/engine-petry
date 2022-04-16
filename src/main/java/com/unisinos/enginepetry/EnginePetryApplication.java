package com.unisinos.enginepetry;

import com.unisinos.enginepetry.model.Lugar;
import com.unisinos.enginepetry.model.RedePetry;
import com.unisinos.enginepetry.model.Transicao;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.unisinos.enginepetry.model.TipoConexaoEnum.CONSUMO;
import static com.unisinos.enginepetry.model.TipoConexaoEnum.GERACAO;

@SpringBootApplication
public class EnginePetryApplication {

    public static void main(String[] args) {
        var redePetry = new RedePetry();
        //L 1 0
        redePetry.adicionarLugar(new Lugar("1", 0));
        //T 1
        redePetry.adicionarTransicao(new Transicao("1"));
        //L 2 0
        redePetry.adicionarLugar(new Lugar("2", 0));
        //C C L1 T1
        redePetry.adicionarConexao(1, CONSUMO, "L1", "T1");
        //C G T1 L2
        redePetry.adicionarConexao(1, GERACAO, "L2", "T1");

        System.out.println(redePetry.getRedeString());

        redePetry.executaTransicao("1");
    }

}
