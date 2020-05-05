package br.com.raia.drogasil.cadastro.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.raia.drogasil.cadastro.config.validacao.BusinessException;
import br.com.raia.drogasil.cadastro.config.validacao.ResourceNotFoundException;
import br.com.raia.drogasil.cadastro.domain.dto.ClienteDTO;
import br.com.raia.drogasil.cadastro.domain.model.Cliente;
import br.com.raia.drogasil.cadastro.domain.repository.CidadeRepository;
import br.com.raia.drogasil.cadastro.domain.repository.ClienteRepository;
import br.com.raia.drogasil.cadastro.scenario.ScenarioFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClienteServiceTest {

	@MockBean
	private ClienteRepository clienteRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteService clienteService;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private List<Cliente> listaDeCliente = new ArrayList<Cliente>();

	@Before
	public void antes() {

		this.cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		this.cidadeRepository.save(ScenarioFactory.CIDADE_PASSO_FUNDO);
		this.clienteRepository.save(ScenarioFactory.FABIO);
		this.clienteRepository.save(ScenarioFactory.FABIOCARVALHO);
		listaDeCliente.add(ScenarioFactory.FABIO);
		listaDeCliente.add(ScenarioFactory.FABIOCARVALHO);
	}

	@After
	public void depois() {
		this.clienteRepository.deleteAll();
		this.cidadeRepository.deleteAll();
	}

	@Test
	public void listaDeCliente() {
		when(clienteRepository.findAll()).thenReturn(listaDeCliente);
		List<ClienteDTO> listaCliente = clienteService.listarClientes();
		assertThat(listaCliente.get(0).getNome()).isEqualTo(ScenarioFactory.FABIO.getNome());
		assertThat(listaCliente.get(0).getSobrenome()).isEqualTo(ScenarioFactory.FABIO.getSobrenome());
		assertThat(listaCliente.get(0).getNome()).isEqualTo(ScenarioFactory.FABIO.getNome());
	}

	@Test
	public void buscarPorNomeCompletoComSucesso() {
		Optional<Cliente> cliente = Optional.empty();
		cliente = Optional.of(ScenarioFactory.FABIO);
		when(clienteRepository.findByNomeAndSobrenome(ScenarioFactory.FABIO.getNome(),
				ScenarioFactory.FABIO.getSobrenome())).thenReturn(cliente);
		ClienteDTO novoCliente = clienteService.buscarNomeESobrenome(ScenarioFactory.FABIO.getNome(),
				ScenarioFactory.FABIO.getSobrenome());
		assertThat(novoCliente.getNome()).isEqualTo(ScenarioFactory.FABIO.getNome());
		assertThat(novoCliente.getSobrenome()).isEqualTo(ScenarioFactory.FABIO.getSobrenome());
	}

	@Test
	public void buscarPorNomeCompletoSemSucesso() {

		assertThrows(ResourceNotFoundException.class, () -> clienteService.buscarNomeESobrenome("FULANO", "TAL")); 

	}
	
	@Test
	public void AtualizarSemSucesso() {

		assertThrows(BusinessException.class, () -> clienteService.atualizarCliente(ScenarioFactory.ATUALIZAR_FULANO)); 

	}

	@Test
	public void buscarPorNomes() {
		Optional<List<Cliente>> listClientes = Optional.empty();
		listClientes = Optional.of(listaDeCliente);
		when(clienteRepository.findByNome("FABIO")).thenReturn(listClientes);
		List<ClienteDTO> novaListaCliente = clienteService.buscarPorNome(ScenarioFactory.FABIO.getNome());
		assertThat(novaListaCliente.get(0).getNome()).isEqualTo(ScenarioFactory.FABIO.getNome());
	}

	@Test
	public void buscarPorNomesError() {
		assertThrows(ResourceNotFoundException.class,
				() -> clienteService.buscarPorNome(ScenarioFactory.ALEGRETE.getNome()));
	}

	@Test
	public void buscarPorIdSucesso() {
		Optional<Cliente> cliente = Optional.empty();
		cliente = Optional.of(ScenarioFactory.FABIO);
		when(clienteRepository.findById(ScenarioFactory.FABIO.getId())).thenReturn(cliente);
		ClienteDTO novoCliente = clienteService.buscarPorId(ScenarioFactory.FABIO.getId());
		assertThat(novoCliente.getNome()).isEqualTo("FABIO");
	}

	@Test
	public void buscarPorIdErro() {

		assertThrows(ResourceNotFoundException.class, () -> clienteService.buscarPorId(ScenarioFactory.FABIO.getId()));
	}

	@Test
	public void deletarErro() {

		assertThrows(ResourceNotFoundException.class, () -> clienteService.deletar("JOSE", "CARLOS"));
	}

}
