package com.unisinos.enginepetry;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ws/petry")
public class PetryAPI {

	@GetMapping(path = "/ciclo", produces = MediaType.TEXT_PLAIN_VALUE)
	public String ciclo() {
		return EnginePetryApplication.rede.executaCiclo();
	}

	@GetMapping(path = "/atual", produces = MediaType.TEXT_PLAIN_VALUE)
	public String atual() {
		return EnginePetryApplication.rede.getLog().toString();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(path = "/lugar/{idLugar}/token")
	public void insereToken(@PathVariable String idLugar) {
		System.out.println("PETRY " + idLugar);
		EnginePetryApplication.rede.adicionarTokens(idLugar, 1);
	}

}
