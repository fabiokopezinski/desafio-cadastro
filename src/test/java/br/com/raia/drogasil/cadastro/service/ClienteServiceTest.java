package br.com.raia.drogasil.cadastro.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.raia.drogasil.cadastro.config.validacao.BusinessException;
import br.com.raia.drogasil.cadastro.config.validacao.ResourceNotFoundException;
import br.com.raia.drogasil.cadastro.converter.Converter;
import br.com.raia.drogasil.cadastro.domain.dto.ClienteDTO;
import br.com.raia.drogasil.cadastro.domain.form.ClienteAtualizarForm;
import br.com.raia.drogasil.cadastro.domain.form.ClienteForm;
import br.com.raia.drogasil.cadastro.domain.model.Cliente;
import br.com.raia.drogasil.cadastro.repository.CidadeRepository;
import br.com.raia.drogasil.cadastro.repository.ClienteRepository;
import br.com.raia.drogasil.cadastro.scenario.ScenarioFactory;

@RunWith(MockitoJUnitRunner.class)
public class ClienteServiceTest {

	@Mock
	ClienteRepository clienteRepository;

	@Mock
	CidadeRepository cidadeRepository;

	@InjectMocks
	ClienteService clienteService;

	@Mock
	Converter<ClienteForm, Cliente> conversorForm;

	@Mock
	Converter<Cliente, ClienteDTO> conversorCliente;
	
	@Mock
	Converter<ClienteAtualizarForm, Cliente> conversorAtualizar;

	@Test
	public void listarClientes_DadoUmaChamadaAlistarClientes_QuandoEstiverPopulado_EntaoReceboUmaListaDeClientes() {
		
		when(clienteRepository.findAll()).thenReturn(ScenarioFactory.CLIENTE_LIST);
		List<ClienteDTO> listaCliente = clienteService.listarClientes();
		assertNotNull(listaCliente);
		verify(clienteRepository).findAll();
	}

	@Test
	public void buscarNomeESobrenome_DadoUmNomeESobrenomeDeUmClienteQueNaoEstaNaBaseDeDados_QuandoOsParametrosEstiveremDeAcordoComARegraDeNegocio_EntaoReceboResourceNotFound() {
		assertThrows(ResourceNotFoundException.class, () -> clienteService.buscarNomeESobrenome(ScenarioFactory.FULANO.getNome(), ScenarioFactory.FULANO.getSobrenome()));
		verify(clienteRepository).findByNomeAndSobrenome(ScenarioFactory.FULANO.getNome(),
				ScenarioFactory.FULANO.getSobrenome());
	}

	@Test
	public void cadastrar_QuandoClienteJaFoiCadastrado_EntaoReceboBusinessException() {
		when(clienteRepository.findByNomeAndSobrenome(ScenarioFactory.FULANOTAL.getNome(),
				ScenarioFactory.FULANOTAL.getSobrenome())).thenReturn(Optional.of(ScenarioFactory.FULANOTAL));
		assertThrows(BusinessException.class, () -> clienteService.cadastrar(ScenarioFactory.CLIENTE_FORM_EXISTENTE));
		verify(clienteRepository).findByNomeAndSobrenome(ScenarioFactory.FULANOTAL.getNome(),
				ScenarioFactory.FULANOTAL.getSobrenome());

	}
	
	@Test
	public void buscarPorNome_DadoUmNomeInformado_QuandoEstiverDeAcordoComARegraDeNegocio_EntaoReceboOk() {
		when(clienteRepository.findByNome("FABIO")).thenReturn(ScenarioFactory.CLIENTE_OPTIONAL_LIST);
		List<ClienteDTO> novaListaCliente = clienteService.buscarPorNome(ScenarioFactory.FABIOCARVALHO.getNome());
		assertNotNull(novaListaCliente);
		verify(clienteRepository).findByNome("FABIO");
	}

	@Test
	public void buscarPorNome_DadoUmNomeInformado_QuandoProcurarENaoAcharNenhumClienteComEsseNome_EntaoReceboResourceNotFound() {
		assertThrows(ResourceNotFoundException.class,
				() -> clienteService.buscarPorNome(ScenarioFactory.BELTRANO.getNome()));
	}
 
	@Test
	public void buscarPorId_DadoUmId_QuandoIdNaoEstiverNoBanco_EntaoReceboResourceNotFound() {

		assertThrows(ResourceNotFoundException.class, () -> clienteService.buscarPorId(ScenarioFactory.FABIO.getId()));
	}

	@Test
	public void deletarCliente_DadoUmCliente_QuandoClienteNaoEstiverNoBanco_EntaoReceboResourceNotFound() {

		assertThrows(ResourceNotFoundException.class, () -> clienteService.deletar("JOSE", "CARLOS"));
	}
	
}
