package br.com.raia.drogasil.cadastro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.raia.drogasil.cadastro.config.validacao.BusinessException;
import br.com.raia.drogasil.cadastro.config.validacao.ResourceNotFoundException;
import br.com.raia.drogasil.cadastro.model.Cidade;
import br.com.raia.drogasil.cadastro.model.Cliente;
import br.com.raia.drogasil.cadastro.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	private Cliente novoCliente = new Cliente();

	@Autowired
	private CidadeService cidadeService;

	public List<Cliente> listarClientes() {
		return clienteRepository.findAll();
	}

	public Cliente buscarNomeESobrenome(String nome, String sobreNome) {
		return clienteRepository.findByNomeAndSobrenome(nome, sobreNome)
				.orElseThrow(() -> new ResourceNotFoundException("Cliente não foi encontrado"));
	}

	public Cliente buscarPorNome(String nome) {
		Optional<Cliente> nomeCliente=clienteRepository.findByNome(nome);
		System.out.println(nomeCliente.get().getCidade().getEstado());
		if (nomeCliente.isPresent()) {
			return nomeCliente.get();
		}
		throw new ResourceNotFoundException("Não achou nenhum nomes");
	}

	public Cliente cadastrar(Cliente cliente) {
		Optional<Cliente> nomeCompleto = clienteRepository.findByNomeAndSobrenome(cliente.getNome(),
				cliente.getSobrenome());
		cliente.setCidade(carregarCidade(cliente.getCidade()));

		if (nomeCompleto.isPresent()) {

			throw new BusinessException("Cliente já foi cadastrado");

		}
		return clienteRepository.save(cliente);

	}

	public Cliente atualizarCliente(Cliente cliente, Integer id) {
		boolean present = clienteRepository.findById(id).isPresent();
		if (present) {
			novoCliente = clienteRepository.getOne(id);
			novoCliente.setNome(cliente.getNome());
			novoCliente.setSobrenome(cliente.getSobrenome());

			return novoCliente;
		}
		throw new ResourceNotFoundException("Não foi encontrado");
	}

	private Cidade carregarCidade(Cidade cidade) {

		return cidadeService.buscarPorNome(cidade.getNome())
				.orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada"));

	}

	public Optional<Cliente> buscarPorId(Integer id) {
		Optional<Cliente> clienteId = clienteRepository.findById(id);
		System.out.println(clienteId.isPresent());
		System.out.println(clienteId.get().getNome());
		if(clienteId.isPresent())
		{
			return clienteId;
		}
		throw new ResourceNotFoundException("Não achou");
	}
	
	public String deletar(Integer id)
	{
		Optional<Cliente> deletarCliente=clienteRepository.findById(id);
		if(deletarCliente.isPresent())
		{
			clienteRepository.deleteById(deletarCliente.get().getId());
			return "Deletada com sucesso";
		}
		throw new ResourceNotFoundException("Não foi encontrado");
		
		
	}

}
