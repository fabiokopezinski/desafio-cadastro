package br.com.raia.drogasil.cadastro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.raia.drogasil.cadastro.config.validacao.BusinessException;
import br.com.raia.drogasil.cadastro.config.validacao.ResourceNotFoundException;
import br.com.raia.drogasil.cadastro.converter.Converter;
import br.com.raia.drogasil.cadastro.domain.dto.CidadeDTO;
import br.com.raia.drogasil.cadastro.domain.form.CidadeForm;
import br.com.raia.drogasil.cadastro.domain.model.Cidade;
import br.com.raia.drogasil.cadastro.domain.repository.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private Converter<Cidade,CidadeDTO> conversorCidade;
	
	@Autowired
	private Converter<CidadeForm,Cidade> conversorCidadeForm;

	private final String NAO_ACHOU_NENHUM_ESTADO = "Não achou nenhum estado";
	private final String JA_FOI_CADASTRO = "Ja foi cadastrado";
	private final String DELETAR = "Deletada com sucesso";
	private final String NAO_FOI_ENCONTRADO = "Não foi encontrado";

	@Autowired
	private CidadeRepository cidadeRepository;

	public List<CidadeDTO> listaDeCidades() {

		return conversorCidade.toArray(cidadeRepository.findAll(), CidadeDTO.class);
	}

	public List<CidadeDTO> buscarPorEstado(String estado) {

		return conversorCidade.toArray(cidadeRepository.findByEstado(estado.toUpperCase())
				.orElseThrow(() -> new ResourceNotFoundException(NAO_ACHOU_NENHUM_ESTADO)), CidadeDTO.class);

	}

	public CidadeDTO buscarPorCidade(String nome) {

		return conversorCidade.toOutPut(cidadeRepository.findByNome(nome.toUpperCase())
				.orElseThrow(() -> new ResourceNotFoundException(NAO_FOI_ENCONTRADO)), CidadeDTO.class);
 
	}

	public CidadeDTO cadastrar(CidadeForm cidade) {
		String nome = cidade.getNome();
		String estado = cidade.getEstado();
		cidade.setNome(nome.toUpperCase());
		cidade.setEstado(estado.toUpperCase());
		Optional<Cidade> cidadeNome = cidadeRepository.findByNome(cidade.getNome());
		if (cidadeNome.isEmpty()) {

			return conversorCidade.toOutPut(cidadeRepository.save(conversorCidadeForm.toEntity(cidade, Cidade.class)),CidadeDTO.class);
		}

		throw new BusinessException(JA_FOI_CADASTRO); 

	}

	public String deletar(String nome) {

		Optional<Cidade> deletarCidade = cidadeRepository.findByNome(nome.toUpperCase());
		if (deletarCidade.isPresent()) {
			cidadeRepository.deleteById(deletarCidade.get().getId()); 
			return DELETAR;
		}
		throw new ResourceNotFoundException(NAO_FOI_ENCONTRADO);

	}

}
