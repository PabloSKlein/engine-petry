package com.unisinos.enginepetry;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ws/petry")
public class PetryAPI {

	@GetMapping(path = "/ciclo", produces = MediaType.TEXT_PLAIN_VALUE)
	public String ciclo() {
		return EnginepetryApplication.rede.executaCiclo();
	}

}
