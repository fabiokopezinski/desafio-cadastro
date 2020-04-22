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

	@Autowired
	private CidadeService cidadeService;

	public List<Cliente> listarClientes() {
		return clienteRepository.findAll();
	}

	public Cliente buscarNomeESobrenome(String nome, String sobreNome) {
		return clienteRepository.findByNomeAndSobrenome(nome, sobreNome)
				.orElseThrow(() -> new ResourceNotFoundException("Cliente não foi encontrado"));
	}

	public List<Cliente> buscarPorNome(String nome) {
		boolean present = clienteRepository.findByNome(nome).stream().findFirst().isPresent();
		if(present)
		{
			return clienteRepository.findByNome(nome);
		}
		throw new ResourceNotFoundException("Clientes com esse nome não foram encontrados");
	}

	public Cliente cadastrar(Cliente cliente) {
		String nome=cliente.getNome().toUpperCase();
		String sobrenome= cliente.getSobrenome().toUpperCase();
		cliente.setNome(nome);
		cliente.setSobrenome(sobrenome);
		Optional<Cliente> nomeCompleto = clienteRepository.findByNomeAndSobrenome(cliente.getNome(),
				cliente.getSobrenome());
		cliente.setCidade(carregarCidade(cliente.getCidade()));

		if (nomeCompleto.isPresent()) {

			throw new BusinessException("Cliente já foi cadastrado");

		}
		return clienteRepository.save(cliente);

	}

	public Cliente atualizarCliente(Cliente cliente) {
		Cliente novoCliente = clienteRepository.getOne(cliente.getId());
		boolean present = clienteRepository.findById(cliente.getId()).isPresent();
		String nome=cliente.getNome().toUpperCase();
		String sobrenome= cliente.getSobrenome().toUpperCase();
		cliente.setNome(nome);
		cliente.setSobrenome(sobrenome);
		if (present) {
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
		if(clienteId.isPresent())
		{
			return clienteId;
		}
		throw new ResourceNotFoundException("Não achou");
	}
	
	public String deletar(String nome,String sobrenome)
	{
		
		Optional<Cliente> deletarCliente=clienteRepository.findByNomeAndSobrenome(nome.toUpperCase(), sobrenome.toUpperCase());
		if(deletarCliente.isPresent())
		{
			clienteRepository.deleteById(deletarCliente.get().getId());
			return "Deletado com sucesso";
		}
		throw new ResourceNotFoundException("Não foi encontrado");
		
		
	}

}
