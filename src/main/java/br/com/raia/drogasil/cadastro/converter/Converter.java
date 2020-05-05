package br.com.raia.drogasil.cadastro.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Converter<D, S> {

	@Autowired
	private ModelMapper conversor;

	public List<S> toArray (List<D> sourceList,Class<S> destClass) {

		return sourceList.stream().map(entity -> conversor.map(entity, destClass)).collect(Collectors.toList());
	}

	public S toEntity(D sourceClass, Class<S> destClass) {
		return conversor.map(sourceClass, destClass);
	}

	public S toOutPut(D sourceClass, Class<S> destClass) {
		return conversor.map(sourceClass, destClass);
	}

	


}
