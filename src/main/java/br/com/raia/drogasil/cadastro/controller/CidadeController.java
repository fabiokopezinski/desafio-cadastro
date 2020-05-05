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

import br.com.raia.drogasil.cadastro.annotation.DocumentacaoSwaggerCidade;
import br.com.raia.drogasil.cadastro.domain.dto.CidadeDTO;
import br.com.raia.drogasil.cadastro.domain.form.CidadeForm;
import br.com.raia.drogasil.cadastro.service.CidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/cidades")
@Tag(name = "Cidade", description = "API")
public class CidadeController {

	@Autowired
	private CidadeService cidadeService;

	private final String LISTAR_CIDADE_ESTADOS = "Listar cidade e estados";
	private final String LISTA_TODOS_OS_ESTADOS_E_CIDADES = "listas todos os estados e cidades";

	private final String BUSCAR_POR_CIDADE = "Buscar por cidade";
	private final String BUSCA_POR_UMA_CIDADE_ESPECIFICA = "Busca por uma cidade especifica";

	private final String BUSCAR_POR_ESTADO = "Buscar por estado";
	private final String BUSCA_POR_UMA_ESTADO_ESPECIFICA = "Busca por uma estado especifica";

	private final String CADASTRO_DE_UMA_CIDADE = "Cadastro de cidade";
	private final String CADASTRAR_UM_NOVA_CIDADE = "Cadastrar uma nova cidade";

	private final String DELETAR_UMA_CIDADE = "Deletar cidade";
	private final String APAGA_UMA_CIDADE_DO_BANCO = "Apagar uma cidade no banco de dados";

	@Operation(summary = LISTAR_CIDADE_ESTADOS, description = LISTA_TODOS_OS_ESTADOS_E_CIDADES)
	@DocumentacaoSwaggerCidade
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping
	public List<CidadeDTO> listaDeCidades() {
		return cidadeService.listaDeCidades();
	}

	@Operation(summary = BUSCAR_POR_CIDADE, description = BUSCA_POR_UMA_CIDADE_ESPECIFICA)
	@DocumentacaoSwaggerCidade
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/nome")
	public CidadeDTO buscarPorCidade(@RequestParam("nome") String nome) {
		return cidadeService.buscarPorCidade(nome);
	}

	@Operation(summary = BUSCAR_POR_ESTADO, description = BUSCA_POR_UMA_ESTADO_ESPECIFICA)
	@DocumentacaoSwaggerCidade
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/estado")
	public List<CidadeDTO> buscarPorEstado(@RequestParam("estado") String estado) {

		return cidadeService.buscarPorEstado(estado);
	}

	@Operation(summary = CADASTRO_DE_UMA_CIDADE, description = CADASTRAR_UM_NOVA_CIDADE)
	@DocumentacaoSwaggerCidade
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	@Transactional
	public CidadeDTO cadastrarCidade(@RequestBody @Valid CidadeForm cidadeForm) {
		return cidadeService.cadastrar(cidadeForm);  
	}

	@Operation(summary = DELETAR_UMA_CIDADE, description = APAGA_UMA_CIDADE_DO_BANCO)
	@DocumentacaoSwaggerCidade
	@ResponseStatus(code = HttpStatus.OK)
	@DeleteMapping
	@Transactional
	public String deletarCidade(@RequestParam("nome") String nome) {

		return cidadeService.deletar(nome);
	}
}
