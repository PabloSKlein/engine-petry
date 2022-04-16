package com.unisinos.enginepetry;

import com.unisinos.enginepetry.model.Lugar;
import com.unisinos.enginepetry.model.RedePetry;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EnginePetryApplication {

    public static void main(String[] args) {
        var redePetry = new RedePetry();
        //L 1 0
        redePetry.adicionarLugar(new Lugar("1", 0));
    }

}
