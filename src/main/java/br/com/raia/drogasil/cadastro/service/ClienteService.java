package br.com.raia.drogasil.cadastro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.raia.drogasil.cadastro.annotation.Constantes;
import br.com.raia.drogasil.cadastro.config.validacao.BusinessException;
import br.com.raia.drogasil.cadastro.config.validacao.ResourceNotFoundException;
import br.com.raia.drogasil.cadastro.converter.Converter;
import br.com.raia.drogasil.cadastro.domain.dto.ClienteDTO;
import br.com.raia.drogasil.cadastro.domain.form.ClienteAtualizarForm;
import br.com.raia.drogasil.cadastro.domain.form.ClienteForm;
import br.com.raia.drogasil.cadastro.domain.model.Cidade;
import br.com.raia.drogasil.cadastro.domain.model.Cliente;
import br.com.raia.drogasil.cadastro.repository.CidadeRepository;
import br.com.raia.drogasil.cadastro.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private Converter<Cliente, ClienteDTO> conversorCliente;

	@Autowired
	private Converter<ClienteForm, Cliente> conversorForm;

	@Autowired
	private Converter<ClienteAtualizarForm, Cliente> conversorAtualizar;

	@Autowired
	private CidadeRepository cidadeRepository;

	public List<ClienteDTO> listarClientes() {
		return conversorCliente.toArray((List<Cliente>) clienteRepository.findAll(), ClienteDTO.class);
	}

	public ClienteDTO buscarNomeESobrenome(String nome, String sobreNome) {

		return conversorCliente.toOutPut(
				clienteRepository.findByNomeAndSobrenome(nome.toUpperCase(), sobreNome.toUpperCase()).orElseThrow(
						() -> new ResourceNotFoundException(Constantes.CLIENTE_NAO_FOI_ENCONTRADO)),
				ClienteDTO.class);
	}

	public List<ClienteDTO> buscarPorNome(String nome) {
		return conversorCliente.toArray(
				clienteRepository.findByNome(nome.toUpperCase())
						.orElseThrow(() -> new ResourceNotFoundException(Constantes.CLIENTES_NAO_FORAM_ENCONTRADOS)),
				ClienteDTO.class);

	}

	public ClienteDTO cadastrar(ClienteForm clienteForm) {
		Optional<Cliente> acharNome = clienteRepository.findByNomeAndSobrenome(clienteForm.getNome().toUpperCase(),
				clienteForm.getSobrenome().toUpperCase());
		
		if (acharNome.isEmpty()) {

			Cliente cliente = conversorForm.toEntity(clienteForm, Cliente.class);
			cliente.setCidade(buscarCidade(clienteForm));
			return conversorCliente.toOutPut(clienteRepository.save(cliente), ClienteDTO.class);

		}

		throw new BusinessException(Constantes.JA_FOI_REGISTRADO);

	}

	private Cidade buscarCidade(ClienteForm clienteForm) {
		return cidadeRepository.findByNome(clienteForm.getCidade().getNome().toUpperCase())
				.orElseThrow(() -> new ResourceNotFoundException(Constantes.NAO_FOI_ENCONTRADO));
	}

	public ClienteDTO atualizarCliente(ClienteAtualizarForm clienteAtualizarForm) {
		Cliente cliente = conversorAtualizar.toEntity(clienteAtualizarForm, Cliente.class);
		boolean present = clienteRepository.findById(cliente.getId()).isPresent();
		Optional<Cliente> buscarNome = clienteRepository.findByNomeAndSobrenome(clienteAtualizarForm.getNome(),
				clienteAtualizarForm.getSobrenome());
		if (present && !buscarNome.isPresent()) {
			cliente.setNome(clienteAtualizarForm.getNome().toUpperCase());
			cliente.setSobrenome(clienteAtualizarForm.getSobrenome().toUpperCase());
			Optional<Cliente> novoCliente = clienteRepository.findById(cliente.getId());
			novoCliente.get().setNome(cliente.getNome());
			novoCliente.get().setSobrenome(cliente.getSobrenome());
			return conversorCliente.toOutPut(novoCliente.get(), ClienteDTO.class);
		} else if (!present) {
			throw new ResourceNotFoundException(Constantes.CLIENTE_NAO_FOI_ENCONTRADO);
		} else {
			throw new BusinessException(Constantes.JA_EXISTE);
		}

	}

	public ClienteDTO buscarPorId(Integer id) {
		return conversorCliente.toOutPut(
				clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constantes.NAO_ACHOU)),
				ClienteDTO.class);
	}

	public String deletar(String nome, String sobrenome) {

		Cliente deletarCliente = clienteRepository.findByNomeAndSobrenome(nome.toUpperCase(),
				sobrenome.toUpperCase()).stream().findFirst().orElseThrow(()->new ResourceNotFoundException(Constantes.NAO_FOI_ENCONTRADO));
		clienteRepository.deleteById(deletarCliente.getId());
		return Constantes.DELETAR;
		
	}

}
