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

import br.com.raia.drogasil.cadastro.annotation.DocumentacaoSwaggerCliente;
import br.com.raia.drogasil.cadastro.domain.dto.ClienteDTO;
import br.com.raia.drogasil.cadastro.domain.form.ClienteAtualizarForm;
import br.com.raia.drogasil.cadastro.domain.form.ClienteForm;
import br.com.raia.drogasil.cadastro.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Cliente", description = "API")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	private final String LISTAR_CLIENTES = "Listar clientes";
	private final String LISTAGEM_DE_CLIENTES = "Listagem de clientes";

	private final String BUSCAR_CLIENTE_POR_ID = "Buscar cliente por ID";
	private final String FAZ_UMA_BUSCAR_POR_ID = "Faz uma buscar por um cliente pelo ID";

	private final String BUSCAR_CLIENTE_POR_NOME_COMPLETO = "Buscar cliente por nome completo";
	private final String FAZ_UMA_BUSCAR_PELO_NOME_COMPLETO = "Faz uma busca pelo nome completo";

	private final String BUSCAR_CLIENTES_PELO_NOME = "Buscar clientes pelo nome";
	private final String FAZ_UMA_BUSCA_POR_CLIENTES_PELO_MESMO_NOME = "Faz uma busca por clientes pelo mesmo nome";

	private final String ATUALIZAR_DADOS_CLIENTE = "Atualizar dados do cliente";
	private final String ATUALIZA_O_NOME_DO_CLIENTE = "Atualiza o nome do cliente";
	private final String CADASTRO_NOVO_CLIENTE = "Cadastro novo cliente";
	private final String REALIZAR_NOVO_CADASTRO = "Realiza um novo cadastro de algum cliente";

	private final String DELETAR = "Deletar cliente";
	private final String APAGAR_NO_BANCO = "Apagar um cliente do banco de dados";

	@Operation(summary = LISTAR_CLIENTES, description = LISTAGEM_DE_CLIENTES)
	@DocumentacaoSwaggerCliente
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping
	public List<ClienteDTO> listarClientes() {
		return clienteService.listarClientes();
	}

	@Operation(summary = BUSCAR_CLIENTE_POR_ID, description = FAZ_UMA_BUSCAR_POR_ID)
	@DocumentacaoSwaggerCliente
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/{id}")
	public ClienteDTO buscarPorId(@PathVariable Integer id) {
		return clienteService.buscarPorId(id); 
	}

	@Operation(summary = BUSCAR_CLIENTE_POR_NOME_COMPLETO, description = FAZ_UMA_BUSCAR_PELO_NOME_COMPLETO)
	@DocumentacaoSwaggerCliente
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/nomecompleto")
	public ClienteDTO buscarPorNomeCompleto(@RequestParam("nome") String nome,
			@RequestParam("sobrenome") String sobrenome) {
		return clienteService.buscarNomeESobrenome(nome, sobrenome);
	}

	@Operation(summary = BUSCAR_CLIENTES_PELO_NOME, description = FAZ_UMA_BUSCA_POR_CLIENTES_PELO_MESMO_NOME)
	@DocumentacaoSwaggerCliente
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/nome")
	public List<ClienteDTO> buscarPorNome(@RequestParam("nome") String nome) {
		return clienteService.buscarPorNome(nome);
	}

	@Operation(summary = ATUALIZAR_DADOS_CLIENTE, description = ATUALIZA_O_NOME_DO_CLIENTE)
	@DocumentacaoSwaggerCliente
	@PutMapping
	@ResponseStatus(code = HttpStatus.OK)
	@Transactional
	public ClienteDTO atualizarCliente(@RequestBody @Valid ClienteAtualizarForm atualizarForm) {
		return clienteService.atualizarCliente(atualizarForm);
	}

	@Operation(summary = CADASTRO_NOVO_CLIENTE, description = REALIZAR_NOVO_CADASTRO)
	@DocumentacaoSwaggerCliente
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	@Transactional
	public ClienteDTO cadastrar(@RequestBody @Valid ClienteForm clienteForm) {
		return clienteService.cadastrar(clienteForm);
	}

	@Operation(summary = DELETAR, description = APAGAR_NO_BANCO)
	@DocumentacaoSwaggerCliente
	@ResponseStatus(code = HttpStatus.OK)
	@DeleteMapping
	@Transactional
	public String deletarCliente(@RequestParam("nome") String nome, @RequestParam("sobrenome") String sobrenome) {
		return clienteService.deletar(nome, sobrenome);
	}

}
