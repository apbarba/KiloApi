package com.salesianostriana.dam.kiloapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(
		description = "API de gestión de la Campaña del Kilo.",
		version = "1.0",
		contact = @Contact(email = "ruiz.lomar22@triana.salesianos.edu", name = "Mario Ruiz"),
		title = "Kilo API",
		license = @License(name = "Licencia de Kilo API")
)
)
public class KiloapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KiloapiApplication.class, args);
	}

}
