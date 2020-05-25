package br.com.raia.drogasil.cadastro.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import br.com.raia.drogasil.cadastro.domain.dto.CidadeDTO;
import br.com.raia.drogasil.cadastro.featurebase.FeatureBase;
import br.com.raia.drogasil.cadastro.repository.CidadeRepository;
import br.com.raia.drogasil.cadastro.repository.ClienteRepository;
import br.com.raia.drogasil.cadastro.scenario.ScenarioFactory;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CidadeIntegracaoTest extends FeatureBase {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	ResponseEntity<List<CidadeDTO>> listaEntity;
	ResponseEntity<CidadeDTO> entity;
	String cidade;
	String estado;
	int httpCodeStatusExpected;

	@Dado("que estou no listar cidades")
	public void listaDeCidades_PopularBanco() {

		this.cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		this.cidadeRepository.save(ScenarioFactory.CIDADE_PASSO_FUNDO);

	}

	@Quando("eu realizado uma chamada")
	public void listaDeCidades_RealizarChamada() {
		listaEntity = listaDeCidades();
	}

	@Então("recebo uma lista")
	public void listaDeCidades_ReceboUmaLista() {
		assertEquals(ScenarioFactory.CIDADE_PORTO_ALEGRE.getNome(), listaEntity.getBody().get(0).getNome());
		assertEquals(listaEntity.getBody().get(0).getEstado(), ScenarioFactory.CIDADE_PORTO_ALEGRE.getEstado());
		this.httpCodeStatusExpected = HttpStatus.OK.value();

	}

	@Então("recebo o status da lista {int}")
	public void listaDeCidades_ReceboOStatusDaLista(Integer int1) {

		assertEquals(listaEntity.getStatusCodeValue(), httpCodeStatusExpected);
	}

	@Dado("uma cidade para cadastrar")
	public void cadastrarCidade_DadoUmaNovaCidadeHaSerCadastrada() {
		entity = cadastrarCidade(ScenarioFactory.CIDADE_GRAVATAI);

	}

	

	@Então("cadastro uma cidade")
	public void cadastrarCidade_EntaoReceboUmaCidade() {
		assertEquals(entity.getBody().getNome(), ScenarioFactory.CIDADE_GRAVATAI.getNome());
		assertEquals(entity.getBody().getEstado(), ScenarioFactory.CIDADE_GRAVATAI.getEstado());
		this.httpCodeStatusExpected = HttpStatus.CREATED.value();
	}
	
	
	@Dado("uma cidade para cadastrar que já existe")
	public void cadastrarCidade_DadoUmaCidadeJaExistenteNoBanco() {
		this.clienteRepository.deleteAll();
		this.cidadeRepository.deleteAll();
		this.cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE);

	}

	@Então("cadastro uma cidade já existente")
	public void cadastrarCidade_EntaoReceboBusinessException() {
		assertThrows(HttpClientErrorException.BadRequest.class,
				() -> entity = cadastrarCidade(ScenarioFactory.CIDADE_JAEXISTE));
	}

	@Então("recebo o status {int}")
	public void cadastrarCidade_EntaoReceboOstatusOk(Integer int1) {
		assertEquals(entity.getStatusCodeValue(), httpCodeStatusExpected);
	}
	

	@Dado("uma cidade para realizar uma busca")
	public void buscarCidade_DadoUmaCidadeParaRealizarUmaBusca() {
		this.clienteRepository.deleteAll();
		this.cidadeRepository.deleteAll();
		this.cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE);

		cidade = ScenarioFactory.CIDADE_PORTO_ALEGRE.getNome();
	}

	@Quando("eu realizar a busca")
	public void buscarCidade_QuandoRealizarABusca() {
		entity = buscarPorCidade(cidade);
	}

	@Então("recebo uma cidade")
	public void buscarCidade_EntaoReceboUmaCidade() {
		assertEquals(cidade, entity.getBody().getNome());
		this.httpCodeStatusExpected = HttpStatus.OK.value();
	}

	@Dado("uma cidade para realizar uma busca que não existe")
	public void umaCidadeParaRealizarUmaBuscaQueNãoExiste() {
		cidade = ScenarioFactory.CIDADE_GRAVATAI.getNome();
	}

	@Então("eu realizo um busca por uma cidade que não existe no banco")
	public void euRealizarABuscaUmaBuscaQueNãoExiste() {

		assertThrows(HttpClientErrorException.NotFound.class, () -> entity = buscarPorCidade(cidade));
	}
	
	@Dado("um estado que devo realizar a busca")
	public void buscarPorEstado_DadoUmEstadoQueDevoRealizarABusca() {
		this.clienteRepository.deleteAll();
		this.cidadeRepository.deleteAll();
		this.cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		this.cidadeRepository.save(ScenarioFactory.CIDADE_PASSO_FUNDO);
		estado=ScenarioFactory.RIO_GRANDE_DO_SUL;
	}

	@Quando("eu realizar a busca do estado")
	public void buscarPorEstado_QuandoEuRealizarABuscaDoEstado() {
	   listaEntity=buscarPorEstado(estado);
	}

	@Dado("um estado para realizar uma busca que não existe")
	public void buscarPorEstado_DadoUmEstadoParaRealizarUmaBuscaQueNãoExiste() {
	  estado=ScenarioFactory.SAO_PAULO;
	}

	@Então("eu realizo um busca por um estado que não existe no banco")
	public void buscarPorEstado_EntaoEuRealizoUmBuscaPorUmEstadoQueNãoExisteNoBanco() {
		assertThrows(HttpClientErrorException.NotFound.class, () -> listaEntity = buscarPorEstado(estado));
	}

	

}
