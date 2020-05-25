package br.com.raia.drogasil.cadastro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.raia.drogasil.cadastro.annotation.Constantes;
import br.com.raia.drogasil.cadastro.config.validacao.BusinessException;
import br.com.raia.drogasil.cadastro.config.validacao.ResourceNotFoundException;
import br.com.raia.drogasil.cadastro.converter.Converter;
import br.com.raia.drogasil.cadastro.domain.dto.CidadeDTO;
import br.com.raia.drogasil.cadastro.domain.form.CidadeForm;
import br.com.raia.drogasil.cadastro.domain.model.Cidade;
import br.com.raia.drogasil.cadastro.repository.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private Converter<Cidade,CidadeDTO> conversorCidade;
	
	@Autowired
	private Converter<CidadeForm,Cidade> conversorCidadeForm;

	@Autowired
	private CidadeRepository cidadeRepository;

	public List<CidadeDTO> listaDeCidades() {

		return conversorCidade.toArray((List<Cidade>) cidadeRepository.findAll(), CidadeDTO.class);
	}

	public List<CidadeDTO> buscarPorEstado(String estado) {

		return conversorCidade.toArray(cidadeRepository.findByEstado(estado.toUpperCase())
				.orElseThrow(() -> new ResourceNotFoundException(Constantes.NAO_ACHOU_NENHUM_ESTADO)), CidadeDTO.class);

	}

	public CidadeDTO buscarPorCidade(String nome) {

		return conversorCidade.toOutPut(cidadeRepository.findByNome(nome.toUpperCase())
				.orElseThrow(() -> new ResourceNotFoundException(Constantes.NAO_FOI_ENCONTRADO)), CidadeDTO.class);
 
	}

	public CidadeDTO cadastrar(CidadeForm cidade) {
		
		Optional<Cidade> cidadeNome = cidadeRepository.findByNome(cidade.getNome());
		if (cidadeNome.isEmpty()) {

			return conversorCidade.toOutPut(cidadeRepository.save(conversorCidadeForm.toEntity(cidade, Cidade.class)),CidadeDTO.class);
		}

		throw new BusinessException(Constantes.JA_FOI_CADASTRO); 

	}

	public String deletar(String nome) {

		Optional<Cidade> deletarCidade = cidadeRepository.findByNome(nome.toUpperCase());
		if (deletarCidade.isPresent()) {
			cidadeRepository.deleteById(deletarCidade.get().getId()); 
			return Constantes.DELETAR;
		}
		throw new ResourceNotFoundException(Constantes.NAO_FOI_ENCONTRADO);

	}

}
