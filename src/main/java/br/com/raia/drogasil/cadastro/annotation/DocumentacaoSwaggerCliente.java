package br.com.raia.drogasil.cadastro.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.raia.drogasil.cadastro.config.validacao.ErroDeFormularioDto;
import br.com.raia.drogasil.cadastro.config.validacao.ErrorResponse;
import br.com.raia.drogasil.cadastro.domain.dto.ClienteDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@ApiResponses(value = {
		@ApiResponse(responseCode = "404", description = "Não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "200", description = "Executado com sucesso",content = @Content(schema = @Schema(implementation = ClienteDTO.class))),
		@ApiResponse(responseCode = "201", description = "Criado com sucesso",content = @Content(schema = @Schema(implementation = ClienteDTO.class))),
		@ApiResponse(responseCode = "400", description = "Algum parâmetro não foi informado", content = @Content(schema = @Schema(implementation = ErroDeFormularioDto.class))),
		@ApiResponse(responseCode = "500", description = "Já cadastrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DocumentacaoSwaggerCliente {

}
