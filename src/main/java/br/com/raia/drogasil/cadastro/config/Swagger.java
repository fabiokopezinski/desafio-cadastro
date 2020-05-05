package br.com.raia.drogasil.cadastro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class Swagger {

	@Bean
	public OpenAPI customOpenAPI() {

		return new OpenAPI()

				.components(new Components())

				.info(new Info().title("Sistema de cadastros").description(

						"Documentação da api de sistema de cadastros").version("1.3"));

	}
}
