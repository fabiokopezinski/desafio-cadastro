package br.com.raia.drogasil.cadastro.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.raia.drogasil.cadastro.config.validacao.ErroDeFormularioDto;
import br.com.raia.drogasil.cadastro.config.validacao.ErrorResponse;
import br.com.raia.drogasil.cadastro.converter.CidadeConverter;
import br.com.raia.drogasil.cadastro.dto.CidadeDto;
import br.com.raia.drogasil.cadastro.form.CidadeForm;
import br.com.raia.drogasil.cadastro.service.CidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/cidades")
public class CidadeController { 

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private CidadeConverter cidadeConverter;

	@Operation(summary = "Listar todos as cidades e estados", description = "Lista todos os estados cadastrados")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de cidades", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CidadeDto.class))))

	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping
	public List<CidadeDto> listaDeCidades() {
		return cidadeConverter.toArray(cidadeService.listaDeCidades());
	}

	@Operation(summary = "Buscar por cidade", description = "Busca por uma cidade especifica")
	@ApiResponses(value = {

			@ApiResponse(responseCode = "200", description = "Cidade encontrada",

					content = @Content(schema = @Schema(implementation = CidadeDto.class))),

			@ApiResponse(responseCode = "404", description = "Cidade não encontrada") })
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/nome")
	public CidadeDto buscarPorCidade(@RequestParam("nome") String nome) {
		return cidadeConverter.toOutPut(cidadeService.buscarPorCidade(nome));
	}

	@Operation(summary = "Buscar por estado", description = "Busca por um estado especifica")
	@ApiResponses(value = {

			@ApiResponse(responseCode = "200", description = "Estado encontrado",

					content = @Content(schema = @Schema(implementation = CidadeDto.class))),

			@ApiResponse(responseCode = "404", description = "Estado não encontrado") })
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/estado")
	public List<CidadeDto> buscarPorEstado(@RequestParam("estado") String estado) {

		return cidadeConverter.toArray(cidadeService.buscarPorEstado(estado));
	}

	@Operation(summary = "Cadastro de cidade", description = "Cadastrar uma nova cidade")
	@ApiResponses(value = {

			@ApiResponse(responseCode = "201", description = "Cidade cadastrada",

					content = @Content(schema = @Schema(implementation = CidadeForm.class))),
			@ApiResponse(responseCode = "400", description = "Algum parâmetro não foi informado", content = @Content(schema = @Schema(implementation = ErroDeFormularioDto.class))),

			@ApiResponse(responseCode = "500", description = "Cidade já cadastrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	@Transactional
	public CidadeDto cadastrarCidade(@RequestBody @Valid CidadeForm cidadeForm) {
		return cidadeConverter.toOutPut(cidadeService.cadastrar(cidadeConverter.toEntity(cidadeForm)));
	}

	@Operation(summary = "Deletar cidade", description = "Apagar uma cidade no banco de dados")
	@ApiResponses(value = {

			@ApiResponse(responseCode = "200", description = "Cidade deletada com sucesso",

					content = @Content(schema = @Schema(implementation = CidadeDto.class))),

			@ApiResponse(responseCode = "404", description = "Não foi encontrada nenhuma cidade com essa nome") })
	@ResponseStatus(code = HttpStatus.OK)
	@DeleteMapping
	@Transactional
	public String deletarCidade(@RequestParam("nome") String nome) {

		return cidadeService.deletar(nome);
	}
}
