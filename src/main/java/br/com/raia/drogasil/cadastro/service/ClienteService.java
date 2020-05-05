package br.com.raia.drogasil.cadastro.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.raia.drogasil.cadastro.config.validacao.BusinessException;
import br.com.raia.drogasil.cadastro.config.validacao.ResourceNotFoundException;
import br.com.raia.drogasil.cadastro.converter.Converter;
import br.com.raia.drogasil.cadastro.domain.dto.ClienteDTO;
import br.com.raia.drogasil.cadastro.domain.form.ClienteAtualizarForm;
import br.com.raia.drogasil.cadastro.domain.form.ClienteForm;
import br.com.raia.drogasil.cadastro.domain.model.Cidade;
import br.com.raia.drogasil.cadastro.domain.model.Cliente;
import br.com.raia.drogasil.cadastro.domain.repository.CidadeRepository;
import br.com.raia.drogasil.cadastro.domain.repository.ClienteRepository;

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

	private final String CLIENTES_NAO_FORAM_ENCONTRADOS = "Clientes com esse nome não foram encontrados";
	private final String CLIENTE_NAO_FOI_ENCONTRADO = "Cliente não foi encontrado"; 
	private final String NAO_ACHOU = "Não achou o ID informado";

	private final String JA_FOI_REGISTRADO = "Cliente já foi cadastrado";
	private final String JA_EXISTE = "Já existe alguém com esse nome";
	private final String DELETADO_COM_SUCESSO="Deletado com sucesso";
	private final String NAO_FOI_ENCONTRADO = "Não foi encontrado";

	@Autowired
	private CidadeRepository cidadeRepository;

	public List<ClienteDTO> listarClientes() {
		return conversorCliente.toArray(clienteRepository.findAll(), ClienteDTO.class);
	}

	public ClienteDTO buscarNomeESobrenome(String nome, String sobreNome) {

		return conversorCliente
				.toOutPut(
						clienteRepository.findByNomeAndSobrenome(nome.toUpperCase(), sobreNome.toUpperCase())
								.orElseThrow(() -> new ResourceNotFoundException(CLIENTE_NAO_FOI_ENCONTRADO)),
						ClienteDTO.class);
	}

	public List<ClienteDTO> buscarPorNome(String nome) {
		return conversorCliente.toArray(clienteRepository.findByNome(nome.toUpperCase())
				.orElseThrow(() -> new ResourceNotFoundException(CLIENTES_NAO_FORAM_ENCONTRADOS)), ClienteDTO.class);

	}

	public ClienteDTO cadastrar(ClienteForm clienteForm) {
		Optional<Cliente> acharNome = clienteRepository.findByNomeAndSobrenome(clienteForm.getNome().toUpperCase(),
				clienteForm.getSobrenome().toUpperCase());
		if (acharNome.isEmpty()) {

			Cliente cliente = conversorForm.toEntity(clienteForm, Cliente.class);
			cliente.setNome(clienteForm.getNome().toUpperCase());
			cliente.setSobrenome(clienteForm.getSobrenome().toUpperCase());
			cliente.setIdade(calcularIdade(cliente.getDataNascimento()));
			cliente.setCidade(buscarCidade(clienteForm));
			return conversorCliente.toOutPut(clienteRepository.save(cliente), ClienteDTO.class);

		} else {

			throw new BusinessException(JA_FOI_REGISTRADO);
		}
	}

	private Cidade buscarCidade(ClienteForm clienteForm) {

		return cidadeRepository.findByNome(clienteForm.getCidade().getNome().toUpperCase())
				.orElseThrow(() -> new ResourceNotFoundException(NAO_FOI_ENCONTRADO));
	}

	private Integer calcularIdade(LocalDate dataNascimento) {
		LocalDate hoje = LocalDate.now();
		Period idade = dataNascimento.until(hoje);
		return idade.getYears();
	}

	public ClienteDTO atualizarCliente(ClienteAtualizarForm clienteAtualizarForm) {
		Cliente cliente = conversorAtualizar.toEntity(clienteAtualizarForm, Cliente.class); 
		boolean present = clienteRepository.findById(cliente.getId()).isPresent();
		Optional<Cliente> buscarNome = clienteRepository.findByNomeAndSobrenome(
				clienteAtualizarForm.getNome().toUpperCase(), clienteAtualizarForm.getSobrenome().toUpperCase());
		if (present && !buscarNome.isPresent()) {
			cliente.setNome(clienteAtualizarForm.getNome().toUpperCase());
			cliente.setSobrenome(clienteAtualizarForm.getSobrenome().toUpperCase());
			Cliente novoCliente = clienteRepository.getOne(cliente.getId());
			novoCliente.setNome(cliente.getNome());
			novoCliente.setSobrenome(cliente.getSobrenome());
			return conversorCliente.toOutPut(novoCliente, ClienteDTO.class);
		}
		throw new BusinessException(JA_EXISTE);
	}

	public ClienteDTO buscarPorId(Integer id) {
		return conversorCliente.toOutPut(
				clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(NAO_ACHOU)),
				ClienteDTO.class); 
	}

	public String deletar(String nome, String sobrenome) {

		Optional<Cliente> deletarCliente = clienteRepository.findByNomeAndSobrenome(nome.toUpperCase(), 
				sobrenome.toUpperCase());
		if (deletarCliente.isPresent()) {
			clienteRepository.deleteById(deletarCliente.get().getId());
			return DELETADO_COM_SUCESSO;
		}
		throw new ResourceNotFoundException(NAO_FOI_ENCONTRADO);

	}

}
