package br.com.raia.drogasil.cadastro.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.raia.drogasil.cadastro.config.validacao.ResourceNotFoundException;
import br.com.raia.drogasil.cadastro.converter.Converter;
import br.com.raia.drogasil.cadastro.domain.dto.CidadeDTO;
import br.com.raia.drogasil.cadastro.domain.form.CidadeForm;
import br.com.raia.drogasil.cadastro.domain.model.Cidade;
import br.com.raia.drogasil.cadastro.repository.CidadeRepository;
import br.com.raia.drogasil.cadastro.scenario.ScenarioFactory;

@RunWith(MockitoJUnitRunner.class)
public class CidadeServiceTeste {

	@Mock
	private CidadeRepository cidadeRepository;
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();	

	@InjectMocks
	CidadeService cidadeService;
	
	@Mock
	private Converter<Cidade, CidadeDTO> conversorCidade;

	@Mock
	private Converter<CidadeForm, Cidade> conversorCidadeForm;

	
	@Test
	public void listaDeCidades_QuandoEstiverPopulado_EntaoDeveRetornarUmaListaComCidades() {
	
		when(cidadeRepository.findAll()).thenReturn(ScenarioFactory.LISTA_CIDADES);
		List<CidadeDTO> listaCidadeService = cidadeService.listaDeCidades();
		assertNotNull(listaCidadeService);
		verify(cidadeRepository).findAll();

	}

	@Test
	public void buscarPorNome__DadoUmNomeDeUmaCidade_QuandoNaoAcharACidadeInformada_EntaoReceboResourceNotFoundException(){
		
		when(cidadeRepository.findByNome(ScenarioFactory.PELOTAS))
		.thenThrow(new ResourceNotFoundException(ScenarioFactory.NAO_FOI_ENCONTRADO));
		assertThrows(ResourceNotFoundException.class, () -> cidadeService.buscarPorCidade(ScenarioFactory.PELOTAS));
		verify(cidadeRepository).findByNome(ScenarioFactory.PELOTAS);

	}

	@Test
	
	public void buscarPorEstado_DadoUmNomeDeUmEstado_QuandoAcharOEstadoInformado_EntaoReceboOEstadoSolicitado()
			throws Exception {
		
		when(cidadeRepository.findByEstado(ScenarioFactory.RIO_GRANDE_DO_SUL))
				.thenReturn(ScenarioFactory.CIDADE_OPTIONAL_LIST);
		List<CidadeDTO> novaListaCidades = cidadeService.buscarPorEstado(ScenarioFactory.RIO_GRANDE_DO_SUL);
		assertNotNull(novaListaCidades);
		verify(cidadeRepository).findByEstado(ScenarioFactory.RIO_GRANDE_DO_SUL);
	}

	@Test
	public void buscarPorEstado_QuandoNaoAcharOEstadoInformado_EntaoReceboResourceNotFoundException() throws Exception {
		assertThrows(ResourceNotFoundException.class, () -> cidadeService.buscarPorCidade(ScenarioFactory.SAO_PAULO));
		verify(cidadeRepository).findByNome(ScenarioFactory.SAO_PAULO);
	}

	@Test
	public void deletar_DadoUmNomeDeUmaCidade_QuandoNaoAcharUmaCidadeQueFoiInformadaParaSerInformada_EntaoReceboResourceNotFoundException()
			throws Exception {
		assertThrows(ResourceNotFoundException.class,() -> cidadeService.deletar(ScenarioFactory.CIDADE_VIAMAO.getNome()));
		
	}

	@Test
	
	public void deletar_DadoUmNomeDeUmaCidade_QuandoAcharUmaCidadeQueFoiInformadaParaSerDeletada_EntaoDeletaACidade() {
		when(cidadeRepository.findByNome(ScenarioFactory.CIDADE_PORTO_ALEGRE.getNome())).thenReturn(ScenarioFactory.CIDADE_OPTIONAL);
		String deletado = cidadeService.deletar(ScenarioFactory.CIDADE_PORTO_ALEGRE.getNome());
		assertEquals(ScenarioFactory.DELETAR, deletado);

	}

}
