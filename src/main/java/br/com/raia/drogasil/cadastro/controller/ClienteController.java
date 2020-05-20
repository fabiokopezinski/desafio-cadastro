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

import br.com.raia.drogasil.cadastro.annotation.Constantes;
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

	@Operation(summary = Constantes.LISTAR_CLIENTES, description = Constantes.LISTAGEM_DE_CLIENTES)
	@DocumentacaoSwaggerCliente
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping
	public List<ClienteDTO> listarClientes() {
		return clienteService.listarClientes();
	}

	@Operation(summary = Constantes.BUSCAR_CLIENTE_POR_ID, description = Constantes.FAZ_UMA_BUSCAR_POR_ID)
	@DocumentacaoSwaggerCliente
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/{id}")
	public ClienteDTO buscarPorId(@PathVariable Integer id) {
		return clienteService.buscarPorId(id);
	}

	@Operation(summary = Constantes.BUSCAR_CLIENTE_POR_NOME_COMPLETO, description = Constantes.FAZ_UMA_BUSCAR_PELO_NOME_COMPLETO)
	@DocumentacaoSwaggerCliente
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/nomecompleto")
	public ClienteDTO buscarPorNomeCompleto(@RequestParam("nome") String nome,
			@RequestParam("sobrenome") String sobrenome) {
		return clienteService.buscarNomeESobrenome(nome, sobrenome);
	}

	@Operation(summary = Constantes.BUSCAR_CLIENTES_PELO_NOME, description = Constantes.FAZ_UMA_BUSCA_POR_CLIENTES_PELO_MESMO_NOME)
	@DocumentacaoSwaggerCliente
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/nome")
	public List<ClienteDTO> buscarPorNome(@RequestParam("nome") String nome) {
		return clienteService.buscarPorNome(nome);
	}

	@Operation(summary = Constantes.ATUALIZAR_DADOS_CLIENTE, description = Constantes.ATUALIZA_O_NOME_DO_CLIENTE)
	@DocumentacaoSwaggerCliente
	@PutMapping
	@ResponseStatus(code = HttpStatus.OK)
	@Transactional
	public ClienteDTO atualizarCliente(@RequestBody @Valid ClienteAtualizarForm atualizarForm) {
		return clienteService.atualizarCliente(atualizarForm);
	}

	@Operation(summary = Constantes.CADASTRO_NOVO_CLIENTE, description = Constantes.REALIZAR_NOVO_CADASTRO)
	@DocumentacaoSwaggerCliente
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	@Transactional
	public ClienteDTO cadastrar(@RequestBody @Valid ClienteForm clienteForm) {
		return clienteService.cadastrar(clienteForm);
	}

	@Operation(summary = Constantes.DELETAR, description = Constantes.APAGAR_NO_BANCO)
	@DocumentacaoSwaggerCliente
	@ResponseStatus(code = HttpStatus.OK)
	@DeleteMapping
	@Transactional
	public String deletarCliente(@RequestParam("nome") String nome, @RequestParam("sobrenome") String sobrenome) {
		return clienteService.deletar(nome, sobrenome);
	}

}
