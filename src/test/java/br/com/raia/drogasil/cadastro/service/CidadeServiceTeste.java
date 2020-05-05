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

import br.com.raia.drogasil.cadastro.config.validacao.ResourceNotFoundException;
import br.com.raia.drogasil.cadastro.domain.dto.CidadeDTO;
import br.com.raia.drogasil.cadastro.domain.model.Cidade;
import br.com.raia.drogasil.cadastro.domain.repository.CidadeRepository;
import br.com.raia.drogasil.cadastro.scenario.ScenarioFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CidadeServiceTeste {

	@MockBean
	private CidadeRepository cidadeRepository;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	private CidadeService cidadeService;

	private List<Cidade> listaCidades = new ArrayList<Cidade>();

	@Before
	public void antes() {

		this.cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		this.cidadeRepository.save(ScenarioFactory.CIDADE_PASSO_FUNDO);

		listaCidades.add(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		listaCidades.add(ScenarioFactory.CIDADE_PASSO_FUNDO);
	}

	@After
	public void depois() {
		this.cidadeRepository.deleteAll();
	}

	@Test
	public void listaDeCidades() {
		when(cidadeRepository.findAll()).thenReturn(listaCidades);
		List<CidadeDTO> listaCidadeService = cidadeService.listaDeCidades();
		assertThat(listaCidadeService.get(0).getNome()).isEqualTo(ScenarioFactory.CIDADE_PORTO_ALEGRE.getNome());
		assertThat(listaCidadeService.get(0).getEstado()).isEqualTo(ScenarioFactory.CIDADE_PORTO_ALEGRE.getEstado());
		assertThat(listaCidadeService.get(1).getNome()).isEqualTo(ScenarioFactory.CIDADE_PASSO_FUNDO.getNome());
		assertThat(listaCidadeService.get(1).getEstado()).isEqualTo(ScenarioFactory.CIDADE_PASSO_FUNDO.getEstado());

	}

	@Test
	public void buscarPorNomeComSucesso() {
		Optional<Cidade> cidade = Optional.empty();
		cidade = Optional.of(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		when(cidadeRepository.findByNome(ScenarioFactory.PORTO_ALEGRE)).thenReturn(cidade);
		CidadeDTO novaCidade = cidadeService.buscarPorCidade(ScenarioFactory.PORTO_ALEGRE);
		assertThat(novaCidade.getNome()).isEqualTo(ScenarioFactory.PORTO_ALEGRE);
	}

	@Test
	public void buscarPorNomeSemSucesso() throws Exception {

		assertThrows(ResourceNotFoundException.class, () -> cidadeService.buscarPorEstado(ScenarioFactory.PELOTAS));

	}

	@Test
	public void buscarPorEstadoComSucesso() throws Exception {
		Optional<List<Cidade>> cidade = Optional.empty();
		cidade = Optional.of(listaCidades);
		when(cidadeRepository.findByEstado(ScenarioFactory.RIO_GRANDE_DO_SUL)).thenReturn(cidade);
		List<CidadeDTO> novaListaCidades = cidadeService.buscarPorEstado(ScenarioFactory.RIO_GRANDE_DO_SUL);
		assertThat(novaListaCidades.get(0).getEstado()).isEqualTo(ScenarioFactory.RIO_GRANDE_DO_SUL);
		assertThat(novaListaCidades.get(1).getEstado()).isEqualTo(ScenarioFactory.RIO_GRANDE_DO_SUL);
	}

	@Test
	public void buscarPorEstadoSemSucesso() throws Exception {
		assertThrows(ResourceNotFoundException.class, ()->cidadeService.buscarPorCidade(ScenarioFactory.SAO_PAULO)); 
	}

	@Test
	public void deletarErro() throws Exception {
		assertThrows(ResourceNotFoundException.class, ()->cidadeService.buscarPorCidade(ScenarioFactory.SAO_PAULO)); 
	}

}
