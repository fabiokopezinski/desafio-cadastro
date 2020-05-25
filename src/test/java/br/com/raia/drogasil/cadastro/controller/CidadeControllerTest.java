package br.com.raia.drogasil.cadastro.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.raia.drogasil.cadastro.converter.Converter;
import br.com.raia.drogasil.cadastro.domain.dto.CidadeDTO;
import br.com.raia.drogasil.cadastro.domain.form.CidadeForm;
import br.com.raia.drogasil.cadastro.domain.model.Cidade;
import br.com.raia.drogasil.cadastro.repository.CidadeRepository;
import br.com.raia.drogasil.cadastro.scenario.ScenarioFactory;
import br.com.raia.drogasil.cadastro.service.CidadeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CidadeControllerTest {

	@MockBean
	CidadeRepository cidadeRepository;
	@MockBean
	CidadeService cidadeService;
	@MockBean
	Converter<Cidade, CidadeDTO> conversorCidade;
	@MockBean
	Converter<CidadeForm, CidadeDTO> conversorCidadeForm;

	@Autowired
	CidadeController cidadeController;

	private List<CidadeDTO> listaCidades = new ArrayList<CidadeDTO>();

	@Test
	public void listaDeCidades_DadoUmChamadoParaListarCidades_QuandoEstiverPopulado_EntaoReceboUmLista()
			throws Exception {
		listaCidades.add(conversorCidade.toEntity(ScenarioFactory.CIDADE_PORTO_ALEGRE, CidadeDTO.class));
		listaCidades.add(conversorCidade.toEntity(ScenarioFactory.CIDADE_PASSO_FUNDO, CidadeDTO.class));
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE))
				.thenReturn(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PASSO_FUNDO)).thenReturn(ScenarioFactory.CIDADE_PASSO_FUNDO);
		when(cidadeService.listaDeCidades()).thenReturn(listaCidades);
		List<CidadeDTO> listarCidades = cidadeController.listaDeCidades();
		assertEquals(2, listarCidades.size());
		verify(cidadeService).listaDeCidades();

	}

	@Test
	public void buscarPorCidade_DadoUmNomeDeUmaCidade_QuandoAcharACidadeInformada_EntaoReceboACidadeInformada() {
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE))
				.thenReturn(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		when(cidadeService.buscarPorCidade(ScenarioFactory.CIDADE_PORTO_ALEGRE.getNome()))
				.thenReturn(ScenarioFactory.CIDADEDTO);
		CidadeDTO novaCidade = cidadeService.buscarPorCidade(ScenarioFactory.CIDADE_PORTO_ALEGRE.getNome());
		assertThat(novaCidade.getNome()).isEqualTo(ScenarioFactory.CIDADE_PORTO_ALEGRE.getNome());
		verify(cidadeService).buscarPorCidade(ScenarioFactory.CIDADE_PORTO_ALEGRE.getNome());
	}

	@Test
	public void buscarPorEstado_DadoUmNomeDeUmEstado_QuandoAcharOEstadoInformado_EntaoReceboOEstadoSolicitado() {
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE))
				.thenReturn(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PASSO_FUNDO)).thenReturn(ScenarioFactory.CIDADE_PASSO_FUNDO);
		when(cidadeService.buscarPorEstado(ScenarioFactory.RIO_GRANDE_DO_SUL))
				.thenReturn(ScenarioFactory.CIDADE_OPTIONAL_DTO_LISTA.get());
		List<CidadeDTO> novaListaCidades = cidadeController.buscarPorEstado(ScenarioFactory.RIO_GRANDE_DO_SUL);
		assertEquals(1, novaListaCidades.size());
		verify(cidadeService).buscarPorEstado(ScenarioFactory.RIO_GRANDE_DO_SUL);

	}

	@Test
	public void cadastrarCidade_DadoUmaNovaCidadeHaSerCadastrada_QuandoEstiverDeAcordoComARegraDeNegocio_EntaoEFeitoOCadastro()
			throws Exception {
		when(cidadeRepository.save(ScenarioFactory.CIDADE_VIAMAO)).thenReturn(ScenarioFactory.CIDADE_VIAMAO);
		when(cidadeService.cadastrar(ScenarioFactory.CIDADE_Form)).thenReturn(ScenarioFactory.CIDADE_VIAMAO_DTO);
		CidadeDTO retorno = cidadeController.cadastrarCidade(ScenarioFactory.CIDADE_Form);
		assertThat(retorno.getNome()).isEqualTo(ScenarioFactory.CIDADE_Form.getNome().toUpperCase());
		assertThat(retorno.getEstado()).isEqualTo(ScenarioFactory.CIDADE_Form.getEstado().toUpperCase());
	}

	@Test
	public void deletar_DadoUmNomeDeUmaCidade_QuandoAcharUmaCidadeQueFoiInformadaParaSerDeletada_EntaoDeletaACidade() {
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE))
				.thenReturn(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		when(cidadeService.deletar(ScenarioFactory.CIDADE_PORTO_ALEGRE.getNome())).thenReturn(ScenarioFactory.DELETAR);
		String deletado = cidadeController.deletarCidade(ScenarioFactory.CIDADE_PORTO_ALEGRE.getNome());
		assertEquals(deletado, ScenarioFactory.DELETAR);
	}

}
