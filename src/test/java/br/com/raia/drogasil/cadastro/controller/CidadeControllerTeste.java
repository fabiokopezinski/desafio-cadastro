package br.com.raia.drogasil.cadastro.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.raia.drogasil.cadastro.converter.Converter;
import br.com.raia.drogasil.cadastro.domain.dto.CidadeDTO;
import br.com.raia.drogasil.cadastro.domain.form.CidadeForm;
import br.com.raia.drogasil.cadastro.domain.model.Cidade;
import br.com.raia.drogasil.cadastro.domain.repository.CidadeRepository;
import br.com.raia.drogasil.cadastro.scenario.ScenarioFactory;
import br.com.raia.drogasil.cadastro.service.CidadeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CidadeControllerTeste {

	@MockBean
	private CidadeRepository cidadeRepository;
	@MockBean
	private CidadeService cidadeService;

	@Autowired
	private CidadeController cidadeController;
	@Autowired
	private Converter<Cidade, CidadeDTO> conversorCidade;
	@Autowired
	private Converter<CidadeForm, CidadeDTO> conversorCidadeForm;

	private List<CidadeDTO> listaCidades = new ArrayList<CidadeDTO>();

	@Before
	public void antes() {
		this.cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE); 
		this.cidadeRepository.save(ScenarioFactory.CIDADE_PASSO_FUNDO);

		listaCidades.add(conversorCidade.toEntity(ScenarioFactory.CIDADE_PORTO_ALEGRE, CidadeDTO.class));
		listaCidades.add(conversorCidade.toEntity(ScenarioFactory.CIDADE_PASSO_FUNDO, CidadeDTO.class));

	}

	@After
	public void depois() {
		this.cidadeRepository.deleteAll();
	}

	@Test
	public void listarTodos() throws Exception {
		when(cidadeService.listaDeCidades()).thenReturn(listaCidades);
		List<CidadeDTO> listarCidades = cidadeController.listaDeCidades();
		assertThat(listarCidades.get(0).getNome()).isEqualTo(ScenarioFactory.CIDADE_PORTO_ALEGRE.getNome());
		assertThat(listarCidades.get(0).getEstado()).isEqualTo(ScenarioFactory.CIDADE_PORTO_ALEGRE.getEstado());
		assertThat(listarCidades.get(1).getNome()).isEqualTo(ScenarioFactory.CIDADE_PASSO_FUNDO.getNome());
		assertThat(listarCidades.get(1).getEstado()).isEqualTo(ScenarioFactory.CIDADE_PASSO_FUNDO.getEstado());

	}

	@Test
	public void buscarPorCidadeSucesso() throws Exception {
		Optional<CidadeDTO> cidade = Optional.empty();
		cidade = Optional.of(conversorCidade.toEntity(ScenarioFactory.CIDADE_PORTO_ALEGRE, CidadeDTO.class));
		when(cidadeService.buscarPorCidade(ScenarioFactory.PORTO_ALEGRE)).thenReturn(cidade.get());
		CidadeDTO novaCidade = cidadeService.buscarPorCidade(ScenarioFactory.PORTO_ALEGRE);
		assertThat(novaCidade.getNome()).isEqualTo(ScenarioFactory.PORTO_ALEGRE);
	}

	@Test
	public void buscarEstadosSucesso() throws Exception {

		when(cidadeService.buscarPorEstado(ScenarioFactory.RIO_GRANDE_DO_SUL)).thenReturn(listaCidades);
		List<CidadeDTO> novaListaCidades = cidadeController.buscarPorEstado(ScenarioFactory.RIO_GRANDE_DO_SUL);
		assertThat(novaListaCidades.get(0).getEstado()).isEqualTo(ScenarioFactory.RIO_GRANDE_DO_SUL);
		assertThat(novaListaCidades.get(1).getEstado()).isEqualTo(ScenarioFactory.RIO_GRANDE_DO_SUL);
	}

	@Test
	public void cadastrar() throws Exception {
		when(cidadeService.cadastrar(ScenarioFactory.CIDADE_Form))
				.thenReturn(conversorCidadeForm.toEntity(ScenarioFactory.CIDADE_Form, CidadeDTO.class));
		CidadeDTO retorno = cidadeController.cadastrarCidade(ScenarioFactory.CIDADE_Form);
		assertThat(retorno.getNome()).isEqualTo(ScenarioFactory.CIDADE_Form.getNome());
		assertThat(retorno.getEstado()).isEqualTo(ScenarioFactory.CIDADE_Form.getEstado());
	}

	@Test
	public void deletarCidadeComSucesso() throws Exception {
		when(cidadeService.deletar(ScenarioFactory.PORTO_ALEGRE)).thenReturn(ScenarioFactory.DELETAR);
		String deletado = cidadeController.deletarCidade(ScenarioFactory.PORTO_ALEGRE);
		assertEquals(deletado, ScenarioFactory.DELETAR);
	}

	@Test
	public void deletarCidadeSemSucesso() throws Exception {
		when(cidadeService.deletar(ScenarioFactory.SAO_PAULO)).thenReturn(ScenarioFactory.NAO_FOI_ENCONTRADO);
		String deletado = cidadeController.deletarCidade(ScenarioFactory.SAO_PAULO);
		assertEquals(deletado, ScenarioFactory.NAO_FOI_ENCONTRADO);
	}

}
