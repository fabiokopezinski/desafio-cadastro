package br.com.raia.drogasil.cadastro.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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

import br.com.raia.drogasil.cadastro.converter.Converter;
import br.com.raia.drogasil.cadastro.domain.dto.ClienteDTO;
import br.com.raia.drogasil.cadastro.domain.form.ClienteForm;
import br.com.raia.drogasil.cadastro.domain.model.Cliente;
import br.com.raia.drogasil.cadastro.domain.repository.CidadeRepository;
import br.com.raia.drogasil.cadastro.domain.repository.ClienteRepository;
import br.com.raia.drogasil.cadastro.scenario.ScenarioFactory;
import br.com.raia.drogasil.cadastro.service.ClienteService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@MockBean
	private CidadeRepository cidadeRepository;

	@MockBean
	private ClienteRepository clienteRepository;

	@MockBean
	private ClienteService clienteService;

	@Autowired
	private ClienteController clienteController;

	@Autowired
	private Converter<Cliente, ClienteDTO> conversorCliente;

	@Autowired
	private Converter<ClienteForm, ClienteDTO> conversorClienteForm;

	private List<ClienteDTO> listarCliente = new ArrayList<ClienteDTO>();

	@Before
	public void antes() {

		this.cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		this.cidadeRepository.save(ScenarioFactory.CIDADE_PASSO_FUNDO);
		this.clienteRepository.save(ScenarioFactory.FABIO);
		this.clienteRepository.save(ScenarioFactory.FABIOCARVALHO);

		listarCliente.add(conversorCliente.toEntity(ScenarioFactory.FABIO, ClienteDTO.class));
		listarCliente.add(conversorCliente.toEntity(ScenarioFactory.FABIOCARVALHO, ClienteDTO.class));
	}

	@After
	public void depois() {
		this.clienteRepository.deleteAll();
		this.cidadeRepository.deleteAll();

	}

	@Test
	public void listarTodos() throws Exception {
		when(clienteService.listarClientes()).thenReturn(listarCliente);
		List<ClienteDTO> listaCliente = clienteController.listarClientes();
		assertThat(listaCliente.get(0).getNome()).isEqualTo(ScenarioFactory.FABIO.getNome());
		assertThat(listaCliente.get(0).getSobrenome()).isEqualTo(ScenarioFactory.FABIO.getSobrenome());
		assertThat(listaCliente.get(0).getSexo()).isEqualTo(ScenarioFactory.FABIO.getSexo());
		assertThat(listaCliente.get(0).getDataNascimento()).isEqualTo(ScenarioFactory.FABIO.getDataNascimento());
		assertThat(listaCliente.get(0).getCidade().getNome()).isEqualTo(ScenarioFactory.FABIO.getCidade().getNome());
		assertThat(listaCliente.get(0).getCidade().getEstado())
				.isEqualTo(ScenarioFactory.FABIO.getCidade().getEstado());

		assertThat(listaCliente.get(1).getNome()).isEqualTo(ScenarioFactory.FABIOCARVALHO.getNome());
		assertThat(listaCliente.get(1).getSobrenome()).isEqualTo(ScenarioFactory.FABIOCARVALHO.getSobrenome());
		assertThat(listaCliente.get(1).getSexo()).isEqualTo(ScenarioFactory.FABIOCARVALHO.getSexo());
		assertThat(listaCliente.get(1).getDataNascimento())
				.isEqualTo(ScenarioFactory.FABIOCARVALHO.getDataNascimento());
		assertThat(listaCliente.get(1).getCidade().getNome())
				.isEqualTo(ScenarioFactory.FABIOCARVALHO.getCidade().getNome());
		assertThat(listaCliente.get(1).getCidade().getEstado())
				.isEqualTo(ScenarioFactory.FABIO.getCidade().getEstado());

	}

	@Test
	public void buscarPorIdSucesso() throws Exception {
		when(clienteService.buscarPorId(ScenarioFactory.FABIO.getId()))
				.thenReturn(conversorCliente.toEntity(ScenarioFactory.FABIO, ClienteDTO.class));
		ClienteDTO cliente = clienteController.buscarPorId(ScenarioFactory.FABIO.getId());

		assertThat(cliente.getNome()).isEqualTo(ScenarioFactory.FABIO.getNome());
		assertThat(cliente.getSobrenome()).isEqualTo(ScenarioFactory.FABIO.getSobrenome());
		assertThat(cliente.getSexo()).isEqualTo(ScenarioFactory.FABIO.getSexo());
		assertThat(cliente.getDataNascimento()).isEqualTo(ScenarioFactory.FABIO.getDataNascimento());
		assertThat(cliente.getCidade().getNome()).isEqualTo(ScenarioFactory.FABIO.getCidade().getNome());
		assertThat(cliente.getCidade().getEstado()).isEqualTo(ScenarioFactory.FABIO.getCidade().getEstado());
	}

	@Test
	public void cadastrarUmClienteComSucesso() throws Exception {
		when(clienteService.cadastrar(ScenarioFactory.CLIENTE_NOVO_FULANO))
				.thenReturn(conversorClienteForm.toEntity(ScenarioFactory.CLIENTE_NOVO_FULANO, ClienteDTO.class));
		ClienteDTO cliente = clienteController.cadastrar(ScenarioFactory.CLIENTE_NOVO_FULANO);
		assertThat(cliente.getNome()).isEqualTo(ScenarioFactory.CLIENTE_NOVO_FULANO.getNome());
		assertThat(cliente.getSobrenome()).isEqualTo(ScenarioFactory.CLIENTE_NOVO_FULANO.getSobrenome());
		assertThat(cliente.getSexo()).isEqualTo(ScenarioFactory.CLIENTE_NOVO_FULANO.getSexo());
		assertThat(cliente.getDataNascimento()).isEqualTo(ScenarioFactory.CLIENTE_NOVO_FULANO.getDataNascimento());
		assertThat(cliente.getCidade().getNome()).isEqualTo(ScenarioFactory.CLIENTE_NOVO_FULANO.getCidade().getNome());
		assertThat(cliente.getCidade().getEstado())
				.isEqualTo(ScenarioFactory.CLIENTE_NOVO_FULANO.getCidade().getEstado());
	}

	@Test
	public void buscarPorNomeCompletoSucesso() throws Exception {
		when(clienteService.buscarNomeESobrenome(ScenarioFactory.FABIO.getNome(), ScenarioFactory.FABIO.getSobrenome()))
				.thenReturn(conversorCliente.toEntity(ScenarioFactory.FABIO, ClienteDTO.class));
		ClienteDTO cliente = clienteController.buscarPorNomeCompleto(ScenarioFactory.FABIO.getNome(),
				ScenarioFactory.FABIO.getSobrenome());
		assertThat(cliente.getNome()).isEqualTo(ScenarioFactory.FABIO.getNome());
		assertThat(cliente.getSobrenome()).isEqualTo(ScenarioFactory.FABIO.getSobrenome());
		assertThat(cliente.getSexo()).isEqualTo(ScenarioFactory.FABIO.getSexo());
		assertThat(cliente.getDataNascimento()).isEqualTo(ScenarioFactory.FABIO.getDataNascimento());
		assertThat(cliente.getCidade().getNome()).isEqualTo(ScenarioFactory.FABIO.getCidade().getNome());
		assertThat(cliente.getCidade().getEstado()).isEqualTo(ScenarioFactory.FABIO.getCidade().getEstado());
	}

	@Test
	public void buscarPorNomeSucesso() throws Exception {
		when(clienteService.buscarPorNome(ScenarioFactory.FABIO.getNome())).thenReturn(listarCliente);
		List<ClienteDTO> listaCliente = clienteController.buscarPorNome(ScenarioFactory.FABIO.getNome());
		assertThat(listaCliente.get(0).getNome()).isEqualTo(ScenarioFactory.FABIO.getNome());
		assertThat(listaCliente.get(0).getSobrenome()).isEqualTo(ScenarioFactory.FABIO.getSobrenome());
		assertThat(listaCliente.get(0).getSexo()).isEqualTo(ScenarioFactory.FABIO.getSexo());
		assertThat(listaCliente.get(0).getDataNascimento()).isEqualTo(ScenarioFactory.FABIO.getDataNascimento());
		assertThat(listaCliente.get(0).getCidade().getNome()).isEqualTo(ScenarioFactory.FABIO.getCidade().getNome());
		assertThat(listaCliente.get(0).getCidade().getEstado())
				.isEqualTo(ScenarioFactory.FABIO.getCidade().getEstado());
	}

	@Test
	public void deletarClienteComSucesso() throws Exception {
		when(clienteService.deletar(ScenarioFactory.FABIO.getNome(), ScenarioFactory.FABIO.getSobrenome()))
				.thenReturn(ScenarioFactory.DELETAR);
		String delete = clienteController.deletarCliente(ScenarioFactory.FABIO.getNome(),
				ScenarioFactory.FABIO.getSobrenome());
		assertEquals(ScenarioFactory.DELETAR, delete);
	}

	@Test
	public void deletarClienteSemSucesso() throws Exception {
		when(clienteService.deletar("FULANO", "TAL")).thenReturn(ScenarioFactory.NAO_FOI_ENCONTRADO);
		String delete = clienteController.deletarCliente("FULANO", "TAL");
		assertEquals(ScenarioFactory.NAO_FOI_ENCONTRADO, delete);
	}

}
