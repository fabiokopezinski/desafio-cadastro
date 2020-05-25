package br.com.raia.drogasil.cadastro.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import br.com.raia.drogasil.cadastro.config.validacao.ResourceNotFoundException;
import br.com.raia.drogasil.cadastro.domain.dto.CidadeDTO;
import br.com.raia.drogasil.cadastro.domain.model.Cidade;
import br.com.raia.drogasil.cadastro.repository.CidadeRepository;
import br.com.raia.drogasil.cadastro.scenario.ScenarioFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CidadeServiceTeste {

	@MockBean
	CidadeRepository cidadeRepository;

	@Autowired
	CidadeService cidadeService;

	@Test
	
	public void listaDeCidades_QuandoEstiverPopulado_EntaoDeveRetornarUmaListaComCidades() {
		List<Cidade> listaCidades = new ArrayList<Cidade>();
		listaCidades.add(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE))
				.thenReturn(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		when(cidadeRepository.findAll()).thenReturn(listaCidades);
		List<CidadeDTO> listaCidadeService = cidadeService.listaDeCidades();
		assertThat(listaCidadeService.get(0).getNome()).isEqualTo(ScenarioFactory.CIDADE_PORTO_ALEGRE.getNome());
		assertThat(listaCidadeService.get(0).getEstado()).isEqualTo(ScenarioFactory.CIDADE_PORTO_ALEGRE.getEstado());
		verify(cidadeRepository).findAll();

	}

	@Test
	
	public void buscarPorCidade_DadoUmNomeDeUmaCidade_QuandoAcharACidadeInformada_EntaoReceboACidadeInformada() {
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE))
				.thenReturn(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		when(cidadeRepository.findByNome(ScenarioFactory.CIDADE_PORTO_ALEGRE.getNome())).thenReturn(ScenarioFactory.CIDADE_OPTIONAL);
		CidadeDTO novaCidade = cidadeService.buscarPorCidade(ScenarioFactory.CIDADE_PORTO_ALEGRE.getNome());
		assertThat(novaCidade.getNome()).isEqualTo(ScenarioFactory.CIDADE_PORTO_ALEGRE.getNome());
		verify(cidadeRepository).findByNome(ScenarioFactory.CIDADE_PORTO_ALEGRE.getNome());
	}

	@Test
	
	public void buscarPorNome__DadoUmNomeDeUmaCidade_QuandoNaoAcharACidadeInformada_EntaoReceboResourceNotFoundException() throws Exception {
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE)).thenReturn(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE)).thenReturn(ScenarioFactory.CIDADE_VIAMAO);
		assertThrows(ResourceNotFoundException.class, () -> cidadeService.buscarPorEstado(ScenarioFactory.PELOTAS));
		verify(cidadeRepository).findByEstado(ScenarioFactory.PELOTAS);

	}

	@Test
	
	public void buscarPorEstado_DadoUmNomeDeUmEstado_QuandoAcharOEstadoInformado_EntaoReceboOEstadoSolicitado()
			throws Exception {
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE))
				.thenReturn(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PASSO_FUNDO)).thenReturn(ScenarioFactory.CIDADE_PASSO_FUNDO);
		when(cidadeRepository.findByEstado(ScenarioFactory.RIO_GRANDE_DO_SUL))
				.thenReturn(ScenarioFactory.CIDADE_OPTIONAL_LIST);
		List<CidadeDTO> novaListaCidades = cidadeService.buscarPorEstado(ScenarioFactory.RIO_GRANDE_DO_SUL);
		assertThat(novaListaCidades.get(0).getEstado().toUpperCase()).isEqualTo(ScenarioFactory.RIO_GRANDE_DO_SUL);
		assertThat(novaListaCidades.get(1).getEstado().toUpperCase()).isEqualTo(ScenarioFactory.RIO_GRANDE_DO_SUL);
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
		assertThrows(ResourceNotFoundException.class,
				() -> cidadeService.deletar(ScenarioFactory.CIDADE_VIAMAO.getNome()));

	}

	@Test
	
	public void deletar_DadoUmNomeDeUmaCidade_QuandoAcharUmaCidadeQueFoiInformadaParaSerDeletada_EntaoDeletaACidade() {
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE))
				.thenReturn(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		when(cidadeRepository.findByNome(ScenarioFactory.CIDADE_PORTO_ALEGRE.getNome())).thenReturn(ScenarioFactory.CIDADE_OPTIONAL);
		String deletado = cidadeService.deletar(ScenarioFactory.CIDADE_PORTO_ALEGRE.getNome());
		assertEquals(ScenarioFactory.DELETAR, deletado);

	}

}
