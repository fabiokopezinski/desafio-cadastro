package br.com.raia.drogasil.cadastro.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.raia.drogasil.cadastro.config.validacao.BusinessException;
import br.com.raia.drogasil.cadastro.config.validacao.ResourceNotFoundException;
import br.com.raia.drogasil.cadastro.controller.CidadeController;
import br.com.raia.drogasil.cadastro.controller.ClienteController;
import br.com.raia.drogasil.cadastro.converter.Converter;
import br.com.raia.drogasil.cadastro.domain.dto.ClienteDTO;
import br.com.raia.drogasil.cadastro.domain.form.CidadeForm;
import br.com.raia.drogasil.cadastro.domain.form.ClienteForm;
import br.com.raia.drogasil.cadastro.domain.repository.CidadeRepository;
import br.com.raia.drogasil.cadastro.domain.repository.ClienteRepository;
import br.com.raia.drogasil.cadastro.scenario.ScenarioFactory;
import br.com.raia.drogasil.cadastro.service.ClienteService;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Então;

public class ClienteControllerSteps {

	@Autowired
	private CidadeController cidadeController;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ClienteController clienteController;

	private List<ClienteDTO> listaDeClientes = new ArrayList<ClienteDTO>();

	@Autowired
	private Converter<ClienteForm, ClienteDTO> conversorCliente;

	private ClienteDTO cliente = new ClienteDTO();

	private String status;

	private Integer id;

	@Before
	public void antes() {

		this.cidadeController.cadastrarCidade(ScenarioFactory.CIDADE_Form);
		cliente = clienteService.cadastrar(ScenarioFactory.CLIENTE_NOVO_FULANO);
		this.clienteService.cadastrar(ScenarioFactory.CLIENTE_NOVO_BELTRANO);
		id = cliente.getId();
		listaDeClientes.add(conversorCliente.toEntity(ScenarioFactory.CLIENTE_NOVO_FULANO, ClienteDTO.class));
		listaDeClientes.add(conversorCliente.toEntity(ScenarioFactory.CLIENTE_NOVO_BELTRANO, ClienteDTO.class));

	}

	@After
	public void depois() {
		this.clienteRepository.deleteAll();
		this.cidadeRepository.deleteAll();
	}

	@Então("dado um chamado para o listarCliente, entao eu recebo uma lista")
	public void dadoUmChamadoParaOListarClienteEntaoEuReceboUmaLista() {
		assertEquals(listaDeClientes.get(0).getNome(), clienteController.listarClientes().get(0).getNome());
		assertEquals(listaDeClientes.get(0).getSobrenome(), clienteController.listarClientes().get(0).getSobrenome());

		assertEquals(listaDeClientes.get(1).getNome(), clienteController.listarClientes().get(1).getNome());
		assertEquals(listaDeClientes.get(1).getSobrenome(), clienteController.listarClientes().get(1).getSobrenome());

	}

	@Dado("um nome,sobrenome,data de nascimento,sexo e cidade onde mora")
	public void umNomeSobrenomeDataDeNascimentoSexoECidadeOndeMora() {
		ScenarioFactory.NOVO_CLIENTE.setCidade(new CidadeForm("PORTO ALEGRE", "RIO GRANDE DO SUL"));
		cliente = clienteController.cadastrar(ScenarioFactory.NOVO_CLIENTE);

	}

	@Então("eu realizo o cadastro de um novo cliente")
	public void euRealizoOCadastroDeUmNovoCliente() {

		assertThat(ScenarioFactory.NOVO_CLIENTE.getNome()).isEqualTo(cliente.getNome());

	}

	@Dado("um cliente com nome e sobrenome {string}, {string} e ao realizar a busca")
	public void umClienteComNomeESobrenomeEAoRealizarABusca(String nome, String sobrenome) {
		cliente = clienteController.buscarPorNomeCompleto(nome, sobrenome);
	}

	@Então("eu recebo as informações desse cliente")
	public void euReceboAsInformaçõesDesseCliente() {
		assertEquals(ScenarioFactory.CLIENTE_NOVO_FULANO.getNome(), cliente.getNome());
	}

	@Então("um nome,sobrenome,data de nascimento,sexo e cidade onde mora e esse cliente já foi cadastrado, entao retorna um exception com a a mensagem")
	public void umNomeSobrenomeDataDeNascimentoSexoECidadeOndeMoraEEsseClienteJáFoiCadastradoEntaoRetornaUmExceptionComAAMensagem() {

		assertThrows(BusinessException.class, () -> clienteController.cadastrar(ScenarioFactory.CLIENTE_JA_EXISTE));
	}

	@Então("dado um cliente com nome e sobrenome {string}, {string} e ao realizar a busca não existir, então deve retornar um exception")
	public void dadoUmClienteComNomeESobrenomeEAoRealizarABuscaNãoExistirEntãoDeveRetornarUmException(String nome,
			String sobrenome) {
		assertThrows(ResourceNotFoundException.class, () -> clienteController.buscarPorNomeCompleto(nome, sobrenome));
	}

	@Dado("um nome {string}")
	public void umNome(String nome) {
		listaDeClientes = clienteController.buscarPorNome(nome);
	}

	@Então("deve retornar um lista com o nome informado possuindo todos os clientes com o mesmo nome")
	public void deveRetornarUmListaComONomeInformadoPossuindoTodosOsClientesComOMesmoNome() {
		assertThat(listaDeClientes.get(0).getNome()).isEqualTo(ScenarioFactory.CLIENTE_NOVO_FULANO.getNome());
		assertThat(listaDeClientes.get(0).getSobrenome()).isEqualTo(ScenarioFactory.CLIENTE_NOVO_FULANO.getSobrenome());
		assertThat(listaDeClientes.get(0).getSexo()).isEqualTo(ScenarioFactory.CLIENTE_NOVO_FULANO.getSexo());
		assertThat(listaDeClientes.get(0).getDataNascimento())
				.isEqualTo(ScenarioFactory.CLIENTE_NOVO_FULANO.getDataNascimento());
		assertThat(listaDeClientes.get(0).getCidade().getNome())
				.isEqualTo(ScenarioFactory.CLIENTE_NOVO_FULANO.getCidade().getNome());
		assertThat(listaDeClientes.get(0).getCidade().getEstado())
				.isEqualTo(ScenarioFactory.CLIENTE_NOVO_FULANO.getCidade().getEstado());
	}

	@Então("dado um nome que não possui cadastrado {string}, então retorna um exception")
	public void dadoUmNomeQueNãoPossuiCadastradoEntãoRetornaUmException(String nome) {
		assertThrows(ResourceNotFoundException.class, () -> clienteController.buscarPorNome(nome));
	}

	@Dado("um nome que será deletado no banco")
	public void umNomeQueQueiraDeletar() {
		status = clienteController.deletarCliente(ScenarioFactory.CLIENTE_NOVO_FULANO.getNome(),
				ScenarioFactory.CLIENTE_NOVO_FULANO.getSobrenome());
	}

	@Então("ao deletar é exibido a mensagem {string}")
	public void aoDeletarÉExibidoAMensagem(String mensagem) {
		assertEquals(mensagem, status);

	}

	@Então("dado um nome a ser deletado que não esteja cadastrado {string}, {string} então deve retornar um exception")
	public void dadoUmNomeASerDeletadoQueNãoEstejaCadastradoEntãoDeveRetornarUmException(String nome,
			String sobrenome) {
		assertThrows(ResourceNotFoundException.class, () -> clienteController.buscarPorNomeCompleto(nome, sobrenome));
	}

	@Dado("um cliente que queira atualizar o seu nome {string}, {string}")
	public void umClienteQueQueiraAtualizarOSeuNome(String nome, String sobrenome) {
		ScenarioFactory.ATUALIZAR_FULANO.setId(id);
		ScenarioFactory.ATUALIZAR_FULANO.setNome(nome);
		ScenarioFactory.ATUALIZAR_FULANO.setSobrenome(sobrenome);
		cliente = clienteController.atualizarCliente(ScenarioFactory.ATUALIZAR_FULANO);
	} 

	@Então("deve retornar o nome do cliente atualizado")
	public void deveRetornarONomeDoClienteAtualizado() {
		assertThat(cliente.getNome()).isEqualTo(ScenarioFactory.ATUALIZAR_FULANO.getNome());
		assertThat(cliente.getSobrenome()).isEqualTo(ScenarioFactory.ATUALIZAR_FULANO.getSobrenome());

	} 
	

}
