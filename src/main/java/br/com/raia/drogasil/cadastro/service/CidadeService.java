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

	public List<Cidade> listarEstados() {
		return cidadeRepository.findAll();
	}

	public List<Cidade> buscarPorEstado(String estado) {

		boolean present = cidadeRepository.findByEstado(estado).stream().findFirst().isPresent();
		if (present) {
			return cidadeRepository.findByEstado(estado);
		}
		throw new ResourceNotFoundException("Não achou nenhum estado");
	}

	public Optional<Cidade> buscarPorNome(String nome) {

		Optional<Cidade> nomeDaCidade = cidadeRepository.findByNome(nome);
		if (nomeDaCidade.isPresent()) {
			return nomeDaCidade;
		}
		throw new ResourceNotFoundException("Nome da cidade não encontrada");

	}

	public Optional<Cidade> buscarId(Integer id) {
		Optional<Cidade> cidadeId = cidadeRepository.findById(id);
		if (cidadeId.isPresent()) {
			return cidadeId;
		}

		throw new ResourceNotFoundException("Não achou o Id encontrado");
	}

	public Cidade cadastrar(Cidade cidade) {
		Optional<Cidade> cidadeNome = cidadeRepository.findByNome(cidade.getNome());
		if (cidadeNome.isEmpty()) {

			return cidadeRepository.save(cidade);
		}

		throw new BusinessException("Já foi cadastrado");

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
