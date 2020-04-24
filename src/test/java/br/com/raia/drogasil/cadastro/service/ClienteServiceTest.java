package br.com.raia.drogasil.cadastro.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import br.com.raia.drogasil.cadastro.config.validacao.ResourceNotFoundException;
import br.com.raia.drogasil.cadastro.enumeration.SexoEnum;
import br.com.raia.drogasil.cadastro.model.Cidade;
import br.com.raia.drogasil.cadastro.model.Cliente;
import br.com.raia.drogasil.cadastro.repository.CidadeRepository;
import br.com.raia.drogasil.cadastro.repository.ClienteRepository;
import br.com.raia.drogasil.cadastro.service.ClienteService;

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

	private Cliente fabio;
	private Cliente fabioCarvalho;
	private Cidade portoAlegre;

	@Before
	public void antes() {
		this.cidadeRepository.deleteAll();

		portoAlegre = new Cidade();
		portoAlegre.setNome("PORTO ALEGRE");
		portoAlegre.setEstado("RIO GRANDE DO SUL");
		this.cidadeRepository.save(portoAlegre);

		LocalDate dataNascimento=LocalDate.of(1993, 10, 21);
		fabio = new Cliente();
		fabio.setCidade(portoAlegre);
		fabio.setDataNascimento(dataNascimento);
		fabio.setIdade(26);
		fabio.setNome("FABIO");
		fabio.setSobrenome("KOPEZINSKI");
		fabio.setCidade(portoAlegre);
		fabio.setSexo(SexoEnum.MASCULINO);
		
				
		fabioCarvalho = new Cliente();
		fabioCarvalho.setCidade(portoAlegre);
		fabioCarvalho.setDataNascimento(dataNascimento);
		fabioCarvalho.setIdade(26);
		fabioCarvalho.setNome("FABIO");
		fabioCarvalho.setSobrenome("KOPEZINSKI");
		fabioCarvalho.setCidade(portoAlegre);
		fabioCarvalho.setSexo(SexoEnum.MASCULINO);

		listaDeCliente.add(fabio);
		clienteRepository.save(fabio);
		clienteRepository.save(fabioCarvalho);
	}

	@Test
	public void listaDeCliente() {
		when(clienteRepository.findAll()).thenReturn(listaDeCliente);
		List<Cliente> listaCliente = clienteService.listarClientes();
		assertThat(listaCliente.get(0).getNome()).isEqualTo(fabio.getNome());
		assertThat(listaCliente.get(0).getSobrenome()).isEqualTo(fabio.getSobrenome());
		assertThat(listaCliente.get(0).getCidade().getNome()).isEqualTo(portoAlegre.getNome());
	}

	@Test
	public void buscarPorNomeCompletoComSucesso() {
		Optional<Cliente> cliente = Optional.empty();
		cliente = Optional.of(fabio);
		when(clienteRepository.findByNomeAndSobrenome(fabio.getNome(), fabio.getSobrenome())).thenReturn(cliente);
		Cliente novoCliente = clienteService.buscarNomeESobrenome(fabio.getNome(), fabio.getSobrenome());
		assertThat(novoCliente.getNome()).isEqualTo(fabio.getNome());
		assertThat(novoCliente.getSobrenome()).isEqualTo(fabio.getSobrenome());
	}

	@Test
	public void buscarPorNomeCompletoSemSucesso() {
		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage("Cliente não foi encontrado");
		clienteService.buscarNomeESobrenome("FABIO", "KOPEZINSKI");
	}

	@Test
	public void buscarPorNomes() {
		when(clienteRepository.findByNome("FABIO")).thenReturn(listaDeCliente);
		List<Cliente> novaListaCliente = clienteService.buscarPorNome("FABIO");
		assertThat(novaListaCliente.get(0).getNome()).isEqualTo("FABIO");
	}

	@Test
	public void buscarPorNomesError() {
		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage("Clientes com esse nome não foram encontrados");
		clienteService.buscarPorNome("JOSE");
	}

	@Test
	public void buscarPorIdSucesso() {
		Optional<Cliente> cliente = Optional.empty();
		cliente = Optional.of(fabio);
		when(clienteRepository.findById(fabio.getId())).thenReturn(cliente);
		Cliente novoCliente = clienteService.buscarPorId(fabio.getId());
		assertThat(novoCliente.getNome()).isEqualTo("FABIO");
	}

	@Test
	public void buscarPorIdErro() {
		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage("Não achou");
		clienteService.buscarPorId(fabio.getId());

	}

	@Test
	public void deletarErro() {
		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage("Não foi encontrado");
		clienteService.deletar("JOSE", "CARLOS"); 
	}

//	@Test
//	public void Atualizar() {
//		
//		Optional<Cliente> cliente = Optional.empty();
//		cliente = Optional.of(fabio);
//		when(clienteRepository.findById(fabio.getId())).thenReturn(cliente);
//		when(clienteRepository.findByNomeAndSobrenome("FABIO", "KOPEZINSKI")).thenReturn(cliente);
//		Cliente novoCliente=clienteService.atualizarCliente(fabio);
//		fabio.setNome("FABIO");
//		fabio.setSobrenome("T");
//		assertThat(novoCliente.getId()).isEqualTo(fabio.getId());
//	}

}
