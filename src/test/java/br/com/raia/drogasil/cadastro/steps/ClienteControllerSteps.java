package br.com.raia.drogasil.cadastro.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.raia.drogasil.cadastro.domain.dto.ClienteDTO;
import br.com.raia.drogasil.cadastro.domain.repository.CidadeRepository;
import br.com.raia.drogasil.cadastro.domain.repository.ClienteRepository;
import br.com.raia.drogasil.cadastro.featurebase.FeatureBase;
import br.com.raia.drogasil.cadastro.scenario.ScenarioFactory;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;

public class ClienteControllerSteps extends FeatureBase {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	ResponseEntity<List<ClienteDTO>> listaEntityCliente;
	ResponseEntity<ClienteDTO> entityCliente;
	Integer httpCodeStatusExpected;
	String nome;
	String sobrenome;

	@Dado("que eu devo listar uma lista de clientes cadastrado")
	public void listarClientes_DadoQueEuDevoListarUmaListaDeClientesCadastrado() {
		this.clienteRepository.deleteAll();
		this.cidadeRepository.deleteAll();
		this.cidadeRepository.save(ScenarioFactory.RIO_GRANDE);
		ScenarioFactory.BELTRANO.setCidade(ScenarioFactory.RIO_GRANDE);
		this.clienteRepository.save(ScenarioFactory.BELTRANO);

	}

	@Quando("eu realizo uma chamada")
	public void listarClientes_QuandoEuRealizoUmaChamada() {
		listaEntityCliente = listarClientes();
		this.httpCodeStatusExpected = HttpStatus.OK.value();
	}

	@Então("recebo uma lista de clientes")
	public void listarClientes_EntaoReceboUmaListaDeClientes() {
		assertEquals(listaEntityCliente.getBody().get(0).getNome(), ScenarioFactory.BELTRANO.getNome());
		assertEquals(listaEntityCliente.getBody().get(0).getSobrenome(), ScenarioFactory.BELTRANO.getSobrenome());
		assertEquals(listaEntityCliente.getBody().get(0).getDataNascimento(),
				ScenarioFactory.BELTRANO.getDataNascimento());
		assertEquals(listaEntityCliente.getBody().get(0).getCidade().getNome(),
				ScenarioFactory.BELTRANO.getCidade().getNome());
		assertEquals(listaEntityCliente.getBody().get(0).getCidade().getEstado(),
				ScenarioFactory.BELTRANO.getCidade().getEstado());
		assertEquals(listaEntityCliente.getBody().get(0).getSexo(), ScenarioFactory.BELTRANO.getSexo());
	}

	@Então("recebo o status da lista cliente {int}")
	public void listarClientes_EntaoReceboOStatusDaListaCliente(Integer int1) {
		assertEquals(listaEntityCliente.getStatusCode().value(), httpCodeStatusExpected);
	}

	@Dado("um cliente para cadastrar")
	public void cadastrar_DadoUmClienteParaCadastrar() {
		this.cidadeRepository.save(ScenarioFactory.RIO_GRANDE);
		ScenarioFactory.CLIENTE_NOVO_FULANO.setCidade(ScenarioFactory.RIO_GRANDE_Form);
		entityCliente = cadastrar(ScenarioFactory.CLIENTE_NOVO_FULANO);
	}

	@Então("cadastro um cliente")
	public void cadastrar_EntaoCadastroUmCliente() {
		assertEquals(entityCliente.getBody().getNome(), ScenarioFactory.CLIENTE_NOVO_FULANO.getNome());
		assertEquals(entityCliente.getBody().getSobrenome(), ScenarioFactory.CLIENTE_NOVO_FULANO.getSobrenome());
		assertEquals(entityCliente.getBody().getDataNascimento(),
				ScenarioFactory.CLIENTE_NOVO_FULANO.getDataNascimento());
		assertEquals(entityCliente.getBody().getCidade().getNome(),
				ScenarioFactory.CLIENTE_NOVO_FULANO.getCidade().getNome());
		assertEquals(entityCliente.getBody().getCidade().getEstado(),
				ScenarioFactory.CLIENTE_NOVO_FULANO.getCidade().getEstado());
		assertEquals(entityCliente.getBody().getSexo(), ScenarioFactory.CLIENTE_NOVO_FULANO.getSexo());
		this.httpCodeStatusExpected = HttpStatus.CREATED.value();
	}

	@Então("recebo o status do cliente {int}")
	public void cadastrar_EntaoReceboOStatusDoCliente(Integer int1) {
		assertEquals(httpCodeStatusExpected, entityCliente.getStatusCode().value());
	}

	@Dado("um cliente com nome e sobrenome")
	public void buscarPorNomeCompleto_QuandoUmClienteComNomeESobrenome() {
		this.clienteRepository.deleteAll();
		this.cidadeRepository.deleteAll();
		this.cidadeRepository.save(ScenarioFactory.CIDADE_ALEGRETE);
		ScenarioFactory.FULANO.setCidade(ScenarioFactory.CIDADE_ALEGRETE);
		this.clienteRepository.save(ScenarioFactory.FULANO);

	}

	@Quando("realizar a busca")
	public void buscarPorNomeCompleto_QuandoRealizarABusca() {
		entityCliente = buscarPorNomeCompleto(ScenarioFactory.FULANO.getNome(), ScenarioFactory.FULANO.getSobrenome());
	}

	@Então("eu recebo as informações desse cliente")
	public void buscarPorNomeCompleto_EntaoEuReceboAsInformaçõesDesseCliente() {
		assertEquals(entityCliente.getBody().getNome(), ScenarioFactory.FULANO.getNome());
		assertEquals(entityCliente.getBody().getSobrenome(), ScenarioFactory.FULANO.getSobrenome());
		assertEquals(entityCliente.getBody().getDataNascimento(), ScenarioFactory.FULANO.getDataNascimento());
		assertEquals(entityCliente.getBody().getCidade().getNome(), ScenarioFactory.FULANO.getCidade().getNome());
		assertEquals(entityCliente.getBody().getCidade().getEstado(), ScenarioFactory.FULANO.getCidade().getEstado());
		assertEquals(entityCliente.getBody().getSexo(), ScenarioFactory.FULANO.getSexo());
		this.httpCodeStatusExpected = HttpStatus.OK.value();
	}
	
}
