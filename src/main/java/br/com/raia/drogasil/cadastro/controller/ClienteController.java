package br.com.raia.drogasil.cadastro.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.raia.drogasil.cadastro.converter.ClienteConverter;
import br.com.raia.drogasil.cadastro.dto.ClienteDto;
import br.com.raia.drogasil.cadastro.form.ClienteAtualizarForm;
import br.com.raia.drogasil.cadastro.form.ClienteForm;
import br.com.raia.drogasil.cadastro.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ClienteConverter clienteConverter;
	
	@GetMapping
	public List<ClienteDto> listarClientes()
	{
		return clienteConverter.toArray(clienteService.listarClientes());
	}
	
	@GetMapping("/{id}")
	public ClienteDto buscarPorId(@PathVariable Integer id)
	{
		return clienteConverter.toEntity(clienteService.buscarPorId(id));
	}
	
	@GetMapping("/nome/completo")
	public ClienteDto buscarPorNomeCompleto(@RequestParam("nome") String nome,@RequestParam("sobrenome") String sobrenome)
	{
		return clienteConverter.toEntity(clienteService.buscarNomeESobrenome(nome, sobrenome));
	}
	
	@GetMapping("/nome")
	public List<ClienteDto> buscarPorNome(@RequestParam("nome") String nome)
	{
		return clienteConverter.toArray(clienteService.buscarPorNome(nome));
	}
	
	@PutMapping("/atualizar")
	@Transactional
	public ClienteDto atualizarCliente(@RequestBody @Valid ClienteAtualizarForm atualizarForm)
	{
		return clienteConverter.toOutPut(clienteService.atualizarCliente(clienteConverter.toEntity(atualizarForm)));
	}
	
	
	@PostMapping
	@Transactional
	public ClienteDto cadastrar(@RequestBody @Valid ClienteForm clienteForm)
	{
		return clienteConverter.toOutPut(clienteService.cadastrar(clienteConverter.toEntity(clienteForm)));
	}
	
	@DeleteMapping("/deletar") 
	@Transactional
	public String deletarCliente(@RequestParam("nome") String nome,@RequestParam("sobrenome") String sobrenome)
	{
		return clienteService.deletar(nome,sobrenome);
	}
	
	

}
