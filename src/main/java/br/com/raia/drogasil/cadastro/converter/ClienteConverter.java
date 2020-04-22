package br.com.raia.drogasil.cadastro.converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.raia.drogasil.cadastro.dto.ClienteDto;
import br.com.raia.drogasil.cadastro.form.ClienteAtualizarForm;
import br.com.raia.drogasil.cadastro.form.ClienteForm;
import br.com.raia.drogasil.cadastro.model.Cliente;

@Component
public class ClienteConverter {

	@Autowired
	private ModelMapper conversor;

	public List<ClienteDto> toArray(List<Cliente> cliente) {
		return cliente.stream().map(entity -> conversor.map(entity, ClienteDto.class)).collect(Collectors.toList());
	}

	public ClienteDto toEntity(Optional<Cliente> cliente) {
		return conversor.map(cliente, ClienteDto.class);
	}

	public Cliente toEntity(ClienteAtualizarForm atualizarForm) {
		return conversor.map(atualizarForm, Cliente.class);
	}

	public Cliente toEntity(ClienteForm cliente) {
		return conversor.map(cliente, Cliente.class);
	}

	public ClienteDto toEntity(Cliente cliente) {
		return conversor.map(cliente, ClienteDto.class);
	}

	public ClienteDto toOutPut(Cliente cliente) {
		return conversor.map(cliente, ClienteDto.class);
	}

	public ClienteDto toOutPut(Optional<Cliente> cliente) {
		return conversor.map(cliente, ClienteDto.class);
	}

}
