package br.com.raia.drogasil.cadastro.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.raia.drogasil.cadastro.config.validacao.ErroDeFormularioDto;
import br.com.raia.drogasil.cadastro.config.validacao.ErrorResponse;
import br.com.raia.drogasil.cadastro.converter.ClienteConverter;
import br.com.raia.drogasil.cadastro.dto.CidadeDto;
import br.com.raia.drogasil.cadastro.dto.ClienteDto;
import br.com.raia.drogasil.cadastro.form.ClienteAtualizarForm;
import br.com.raia.drogasil.cadastro.form.ClienteForm;
import br.com.raia.drogasil.cadastro.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/clientes")
public class ClienteController {  

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ClienteConverter clienteConverter;

	@Operation(summary = "Listar clientes", description = "Listagem de clientes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de clientes", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClienteDto.class))))

	}) 
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping
	public List<ClienteDto> listarClientes() {
		return clienteConverter.toArray(clienteService.listarClientes());
	}

	@Operation(summary = "Buscar cliente por ID", description = "Faz uma buscar por um cliente pelo ID")
	@ApiResponses(value = {

			@ApiResponse(responseCode = "200", description = "Cliente encontrado",

					content = @Content(schema = @Schema(implementation = ClienteDto.class))),

			@ApiResponse(responseCode = "404", description = "Cliente não encontrado") })
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/{id}")
	public ClienteDto buscarPorId(@PathVariable Integer id) {
		return clienteConverter.toEntity(clienteService.buscarPorId(id));
	}

	@Operation(summary = "Buscar cliente por nome completo", description = "Faz uma busca pelo nome completo")
	@ApiResponses(value = {

			@ApiResponse(responseCode = "200", description = "Cliente encontrado",

					content = @Content(schema = @Schema(implementation = ClienteDto.class))),

			@ApiResponse(responseCode = "404", description = "Cliente não encontrado") })
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/nomecompleto")
	public ClienteDto buscarPorNomeCompleto(@RequestParam("nome") String nome,
			@RequestParam("sobrenome") String sobrenome) {
		return clienteConverter.toEntity(clienteService.buscarNomeESobrenome(nome, sobrenome));
	}

	@Operation(summary = "Buscar clientes pelo nome", description = "Faz uma busca por clientes pelo mesmo nome")
	@ApiResponses(value = {

			@ApiResponse(responseCode = "200", description = "Clientes encontrados",

					content = @Content(schema = @Schema(implementation = ClienteDto.class))),

			@ApiResponse(responseCode = "404", description = "Clientes não encontrados") })
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/nome")
	public List<ClienteDto> buscarPorNome(@RequestParam("nome") String nome) {
		return clienteConverter.toArray(clienteService.buscarPorNome(nome));
	}

	@Operation(summary = "Atualizar dados do cliente", description = "Atualiza o nome do cliente")
	@ApiResponses(value = {

			@ApiResponse(responseCode = "200", description = "Cliente Atualizado",

					content = @Content(schema = @Schema(implementation = ClienteDto.class))),
			@ApiResponse(responseCode = "400", description = "Algum parâmetro não foi informado", content = @Content(schema = @Schema(implementation = ErroDeFormularioDto.class))),

			@ApiResponse(responseCode = "500", description = "Cliente não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	@ResponseStatus(code = HttpStatus.OK)
	@PutMapping
	@Transactional
	public ClienteDto atualizarCliente(@RequestBody @Valid ClienteAtualizarForm atualizarForm) {
		return clienteConverter.toOutPut(clienteService.atualizarCliente(clienteConverter.toEntity(atualizarForm)));
	}
	
	@Operation(summary = "Cadastro novo cliente", description = "Realiza um novo cadastro de algum cliente")
	@ApiResponses(value = {

			@ApiResponse(responseCode = "201", description = "Cliente cadastrado",

					content = @Content(schema = @Schema(implementation = ClienteDto.class))),
			@ApiResponse(responseCode = "400", description = "Algum parâmetro não foi informado", content = @Content(schema = @Schema(implementation = ErroDeFormularioDto.class))),

			@ApiResponse(responseCode = "500", description = "Cliente já foi cadastrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	@Transactional
	public ClienteDto cadastrar(@RequestBody @Valid ClienteForm clienteForm) {
		return clienteConverter.toOutPut(clienteService.cadastrar(clienteConverter.toEntity(clienteForm)));
	}

	@Operation(summary = "Deletar cliente", description = "Apagar um cliente do banco de dados")
	@ApiResponses(value = {

			@ApiResponse(responseCode = "200", description = "Cliente deletad0 com sucesso",

					content = @Content(schema = @Schema(implementation = CidadeDto.class))),

			@ApiResponse(responseCode = "404", description = "Não foi encontrada nenhuma cliente com essa nome") })
	@ResponseStatus(code = HttpStatus.OK)
	@DeleteMapping
	@Transactional
	public String deletarCliente(@RequestParam("nome") String nome, @RequestParam("sobrenome") String sobrenome) {
		return clienteService.deletar(nome, sobrenome);
	}

}
