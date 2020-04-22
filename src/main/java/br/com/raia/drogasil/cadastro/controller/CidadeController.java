package br.com.raia.drogasil.cadastro.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.raia.drogasil.cadastro.converter.CidadeConverter;
import br.com.raia.drogasil.cadastro.dto.CidadeDto;
import br.com.raia.drogasil.cadastro.form.CidadeForm;
import br.com.raia.drogasil.cadastro.service.CidadeService;

@RestController
@RequestMapping("/cidade")
public class CidadeController {

	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private CidadeConverter cidadeConverter;
	
	@GetMapping
	public List<CidadeDto> listarEstados()
	{
		return cidadeConverter.toArray(cidadeService.listarEstados());
	}
	
	@GetMapping("/estado/{nome}")
	public CidadeDto listarEstados(@PathVariable String nome)
	{
		return cidadeConverter.toOutPut(cidadeService.buscarPorNome(nome));
	}

	@GetMapping("/{estado}")
	public List<CidadeDto> buscarPorEstado(@PathVariable String estado) {
		
		return cidadeConverter.toArray(cidadeService.buscarPorEstado(estado));
	}
	@PostMapping
	@Transactional
	public CidadeDto cadastrarCidade(@RequestBody @Valid CidadeForm cidadeForm)
	{
		return cidadeConverter.toOutPut(cidadeService.cadastrar(cidadeConverter.toEntity(cidadeForm)));
	}
	
	@DeleteMapping("/{nome}")
	@Transactional
	public String deletarCidade(@PathVariable String nome){
		
		 return cidadeService.deletar(nome);
	}
}
