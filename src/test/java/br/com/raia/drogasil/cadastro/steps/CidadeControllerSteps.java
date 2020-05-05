package br.com.raia.drogasil.cadastro.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.raia.drogasil.cadastro.CadastroApplicationTests;
import br.com.raia.drogasil.cadastro.config.validacao.BusinessException;
import br.com.raia.drogasil.cadastro.config.validacao.ResourceNotFoundException;
import br.com.raia.drogasil.cadastro.controller.CidadeController;
import br.com.raia.drogasil.cadastro.domain.dto.CidadeDTO;
import br.com.raia.drogasil.cadastro.domain.form.CidadeForm;
import br.com.raia.drogasil.cadastro.domain.repository.CidadeRepository;
import br.com.raia.drogasil.cadastro.scenario.ScenarioFactory;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Então;

public class CidadeControllerSteps extends CadastroApplicationTests {

	@Autowired
	private CidadeController cidadeController;

	@Autowired
	private CidadeRepository cidadeRepository;

	private CidadeDTO cidade = new CidadeDTO();

	private List<CidadeDTO> listaCidades = new ArrayList<CidadeDTO>();

	private String status;

	@Before
	public void antes() {

		this.cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		this.cidadeRepository.save(ScenarioFactory.CIDADE_PASSO_FUNDO);

	}

	@Então("dado um chamado para o listarCidades, entao eu recebo uma lista")
	public void dadoUmChamadoParaOListarCidadesEntaoEuReceboUmaLista() {

		assertEquals(cidadeController.listaDeCidades().get(0).getNome(), ScenarioFactory.CIDADE_PORTO_ALEGRE.getNome());
		assertEquals(cidadeController.listaDeCidades().get(1).getNome(), ScenarioFactory.CIDADE_PASSO_FUNDO.getNome());



	}

	@Dado("um nome de cidade e estado deve ser cadastrado")
	public void umNomeDeCidadeEEstadoDeveSerCadastrado() {
		cidade = cidadeController.cadastrarCidade(ScenarioFactory.ALEGRETE);
	}

	@Então("recebo um DTO")
	public void receboUmDTO() {
		assertEquals(ScenarioFactory.ALEGRETE.getNome(), cidade.getNome());
		assertEquals(ScenarioFactory.ALEGRETE.getEstado(), cidade.getEstado());
	}

	@Então("dado um nome de cidade e estado deve ser cadastrado {string} , {string} retorna uma exception")
	public void dadoUmNomeDeCidadeEEstadoDeveSerCadastradoRetornaUmaException(String nome, String estado) {
		assertThrows(BusinessException.class, () -> cidadeController.cadastrarCidade(new CidadeForm(nome, estado)));
	}

	@Dado("um nome de uma cidade {string}")
	public void umNomeDeUmaCidade(String nome) {
		cidade = cidadeController.buscarPorCidade(nome);
	}

	@Então("deve retornar a cidade informada")
	public void deveRetornarACidadeInformada() {
		assertEquals(cidade.getNome(), ScenarioFactory.PORTO_ALEGRE);
	}

	@Então("dado um nome de um cidade que não foi cadastrada {string} retorna uma exception")
	public void dadoUmNomeDeUmCidadeQueNãoFoiCadastradaRetornaUmaException(String nome) {
		assertThrows(ResourceNotFoundException.class, () -> cidadeController.buscarPorCidade(nome));
	}

	@Dado("um nome de um estado {string}")
	public void umNomeDeUmEstado(String estado) {
		listaCidades = cidadeController.buscarPorEstado(estado);
	}

	@Então("deve retornar uma lista de cidades desse estado")
	public void deveRetornarUmEstado() {
		assertEquals(listaCidades.get(0).getNome(), listaCidades.get(0).getNome());
		assertEquals(listaCidades.get(0).getEstado(), listaCidades.get(0).getEstado());
		assertEquals(listaCidades.get(1).getNome(), listaCidades.get(1).getNome());
		assertEquals(listaCidades.get(1).getEstado(), listaCidades.get(1).getEstado());

	}

	@Então("dado um nome de um estado que não foi cadastrado {string}, deve retornar uma exception")
	public void dadoUmNomeDeUmEstadoQueNãoFoiCadastradoDeveRetornarUmaException(String estado) {
		assertThrows(ResourceNotFoundException.class, () -> cidadeController.buscarPorEstado(estado));
	}

	@Dado("um nome de uma cidade que deve ser apagado {string}")
	public void umNomeDeUmaCidadeQueDeveSerApagado(String nome) {
		status = cidadeController.deletarCidade(nome);
	}

	@Então("deve retornar uma mensagem {string}")
	public void deveRetornarUmaMensagem(String deletado) {
		assertEquals(deletado, status);
	}

	@Então("dado uma cidade que não esteja nos cadastros, deve retornar a mensagem {string}")
	public void dadoUmaCidadeQueNãoEstejaNosCadastrosDeveRetornarAMensagem(String cidade) {
		assertThrows(ResourceNotFoundException.class, () -> cidadeController.deletarCidade(cidade));
	}

}
