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

import br.com.raia.drogasil.cadastro.annotation.Constantes;
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

	@Operation(summary = Constantes.LISTAR_CIDADE_ESTADOS, description = Constantes.LISTA_TODOS_OS_ESTADOS_E_CIDADES)
	@DocumentacaoSwaggerCidade
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping
	public List<CidadeDTO> listaDeCidades() {
		return cidadeService.listaDeCidades();
	}

	@Operation(summary = Constantes.BUSCAR_POR_CIDADE, description = Constantes.BUSCA_POR_UMA_CIDADE_ESPECIFICA)
	@DocumentacaoSwaggerCidade
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/nome")
	public CidadeDTO buscarPorCidade(@RequestParam("nome") String nome) {
		return cidadeService.buscarPorCidade(nome);
	}

	@Operation(summary = Constantes.BUSCAR_POR_ESTADO, description = Constantes.BUSCA_POR_UMA_ESTADO_ESPECIFICA)
	@DocumentacaoSwaggerCidade
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/estado")
	public List<CidadeDTO> buscarPorEstado(@RequestParam("estado") String estado) {

		return cidadeService.buscarPorEstado(estado);
	}

	@Operation(summary = Constantes.CADASTRO_DE_UMA_CIDADE, description = Constantes.CADASTRAR_UM_NOVA_CIDADE)
	@DocumentacaoSwaggerCidade
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	@Transactional
	public CidadeDTO cadastrarCidade(@RequestBody @Valid CidadeForm cidadeForm) {
		return cidadeService.cadastrar(cidadeForm);
	}

	@Operation(summary = Constantes.DELETAR_UMA_CIDADE, description = Constantes.APAGA_UMA_CIDADE_DO_BANCO)
	@DocumentacaoSwaggerCidade
	@ResponseStatus(code = HttpStatus.OK)
	@DeleteMapping
	@Transactional
	public String deletarCidade(@RequestParam("nome") String nome) {

		return cidadeService.deletar(nome);
	}
}
