package br.com.raia.drogasil.cadastro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.raia.drogasil.cadastro.config.validacao.BusinessException;
import br.com.raia.drogasil.cadastro.config.validacao.ResourceNotFoundException;
import br.com.raia.drogasil.cadastro.model.Cidade;
import br.com.raia.drogasil.cadastro.repository.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	public List<Cidade> listaDeCidades() {
		return cidadeRepository.findAll();  
	}

	public List<Cidade> buscarPorEstado(String estado) {

		List<Cidade> estadoAchado = cidadeRepository.findByEstado(estado.toUpperCase());
		if (estadoAchado.isEmpty()) {
			throw new ResourceNotFoundException("Não achou nenhum estado");
		}
		return estadoAchado;

	}

	public Cidade buscarPorCidade(String nome) { 

		Optional<Cidade> nomeDaCidade = cidadeRepository.findByNome(nome.toUpperCase());
		if (nomeDaCidade.isPresent()) {
			return nomeDaCidade.get();
		}
		throw new ResourceNotFoundException("Nome da cidade não encontrada");

	}

	public Cidade cadastrar(Cidade cidade) {
		String nome = cidade.getNome();
		String estado = cidade.getEstado();
		cidade.setNome(nome.toUpperCase());
		cidade.setEstado(estado.toUpperCase());
		Optional<Cidade> cidadeNome = cidadeRepository.findByNome(cidade.getNome());
		if (cidadeNome.isEmpty()) {

			return cidadeRepository.save(cidade);
		}

		throw new BusinessException("Ja foi cadastrado");

	}

	public String deletar(String nome) {

		Optional<Cidade> deletarCidade = cidadeRepository.findByNome(nome); 
		if (deletarCidade.isPresent()) {
			cidadeRepository.deleteById(deletarCidade.get().getId());
			return "Deletada com sucesso"; 
		}
		throw new ResourceNotFoundException("Não foi encontrado");

	}

}
