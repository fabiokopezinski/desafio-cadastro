package br.com.raia.drogasil.cadastro.converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.raia.drogasil.cadastro.dto.CidadeDto;
import br.com.raia.drogasil.cadastro.form.CidadeForm;
import br.com.raia.drogasil.cadastro.model.Cidade;

@Component
public class CidadeConverter {

	@Autowired
	private ModelMapper conversor;

	public List<CidadeDto> toArray(List<Cidade> listarCidades) {
		return listarCidades.stream().map(entity -> conversor.map(entity, CidadeDto.class))
				.collect(Collectors.toList());
	}

	public Cidade toEntity(CidadeForm cidadeForm) {
		return conversor.map(cidadeForm, Cidade.class);
	}

	public CidadeDto toOutPut(Cidade cidade) {
		return conversor.map(cidade, CidadeDto.class);
	}

	public CidadeDto toOutPut(Optional<Cidade> cidade) {
		return conversor.map(cidade, CidadeDto.class);
	}
}
