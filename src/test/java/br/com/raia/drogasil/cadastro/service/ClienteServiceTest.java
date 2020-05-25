package br.com.raia.drogasil.cadastro.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

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

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClienteServiceTest {

	@MockBean
	ClienteRepository clienteRepository;

	@MockBean
	CidadeRepository cidadeRepository;

	@Autowired
	ClienteService clienteService;

	@MockBean
	Converter<ClienteForm, Cliente> conversorForm;

	private List<Cliente> listaDeCliente = new ArrayList<Cliente>();

	@Test
	public void listarClientes_DadoUmaChamadaAlistarClientes_QuandoEstiverPopulado_EntaoReceboUmaListaDeClientes() {
		listaDeCliente.add(ScenarioFactory.FABIO);
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE))
				.thenReturn(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		when(clienteRepository.save(ScenarioFactory.FABIO)).thenReturn(ScenarioFactory.FABIO);
		when(clienteRepository.findAll()).thenReturn(listaDeCliente);
		List<ClienteDTO> listaCliente = clienteService.listarClientes();
		assertThat(listaCliente.get(0).getNome()).isEqualTo(ScenarioFactory.FABIO.getNome());
		assertThat(listaCliente.get(0).getSobrenome()).isEqualTo(ScenarioFactory.FABIO.getSobrenome());
		verify(clienteRepository).findAll();
	}

	@Test
	public void buscarNomeESobrenome_DadoUmNomeESobrenomeDeUmCliente_QuandoOsParametrosEstiveremDeAcordoComARegraDeNegocio_EntaoReceboOCliente() {
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE))
				.thenReturn(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		when(clienteRepository.save(ScenarioFactory.FABIO)).thenReturn(ScenarioFactory.FABIO);
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE))
				.thenReturn(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		when(clienteRepository.save(ScenarioFactory.FABIO)).thenReturn(ScenarioFactory.FABIO);
		when(clienteRepository.findByNomeAndSobrenome(ScenarioFactory.FABIO.getNome(),
				ScenarioFactory.FABIO.getSobrenome())).thenReturn(ScenarioFactory.CLIENTE_OPTIONAL);
		ClienteDTO novoCliente = clienteService.buscarNomeESobrenome(ScenarioFactory.FABIO.getNome(),
				ScenarioFactory.FABIO.getSobrenome());
		assertThat(novoCliente.getNome()).isEqualTo(ScenarioFactory.FABIO.getNome());
		assertThat(novoCliente.getSobrenome()).isEqualTo(ScenarioFactory.FABIO.getSobrenome());
		verify(clienteRepository).findByNomeAndSobrenome(ScenarioFactory.FABIO.getNome(),
				ScenarioFactory.FABIO.getSobrenome());
	}

	@Test
	public void buscarNomeESobrenome_DadoUmNomeESobrenomeDeUmClienteQueNaoEstaNaBaseDeDados_QuandoOsParametrosEstiveremDeAcordoComARegraDeNegocio_EntaoReceboResourceNotFound() {
		assertThrows(ResourceNotFoundException.class, () -> clienteService.buscarNomeESobrenome(ScenarioFactory.FULANO.getNome(), ScenarioFactory.FULANO.getSobrenome()));
		verify(clienteRepository).findByNomeAndSobrenome(ScenarioFactory.FULANO.getNome(),
				ScenarioFactory.FULANO.getSobrenome());
	}

	@Test
	public void cadastrar_QuandoClienteJaFoiCadastrado_EntaoReceboBusinessException() {
		when(cidadeRepository.save(ScenarioFactory.CIDADE_VIAMAO)).thenReturn(ScenarioFactory.CIDADE_VIAMAO);
		when(clienteRepository.save(ScenarioFactory.FULANOTAL)).thenReturn(ScenarioFactory.FULANOTAL);
		when(clienteRepository.findByNomeAndSobrenome(ScenarioFactory.FULANOTAL.getNome(),
				ScenarioFactory.FULANOTAL.getSobrenome())).thenReturn(Optional.of(ScenarioFactory.FULANOTAL));
		assertThrows(BusinessException.class, () -> clienteService.cadastrar(ScenarioFactory.CLIENTE_FORM_EXISTENTE));
		verify(clienteRepository).findByNomeAndSobrenome(ScenarioFactory.FULANOTAL.getNome(),
				ScenarioFactory.FULANOTAL.getSobrenome());

	}
	
	@Test
	public void buscarPorNome_DadoUmNomeInformado_QuandoEstiverDeAcordoComARegraDeNegocio_EntaoReceboOk() {
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE))
				.thenReturn(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		when(clienteRepository.save(ScenarioFactory.FABIO)).thenReturn(ScenarioFactory.FABIO);
		when(clienteRepository.findByNome("FABIO")).thenReturn(ScenarioFactory.CLIENTE_OPTIONAL_LIST);
		List<ClienteDTO> novaListaCliente = clienteService.buscarPorNome(ScenarioFactory.FABIOCARVALHO.getNome());
		assertThat(novaListaCliente.get(1).getNome()).isEqualTo(ScenarioFactory.FABIOCARVALHO.getNome());
		verify(clienteRepository).findByNome("FABIO");
	}

	@Test
	public void buscarPorNome_DadoUmNomeInformado_QuandoProcurarENaoAcharNenhumClienteComEsseNome_EntaoReceboResourceNotFound() {
		assertThrows(ResourceNotFoundException.class,
				() -> clienteService.buscarPorNome(ScenarioFactory.BELTRANO.getNome()));
	}

	@Test
	public void atualizarCliente_DadoUmCliente_QuandoAtualizar_EntaoReceboOK() {
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE)).thenReturn(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		when(clienteRepository.save(ScenarioFactory.FABIO)).thenReturn(ScenarioFactory.FABIO);
		when(clienteRepository.findById(ScenarioFactory.FABIO.getId())).thenReturn(ScenarioFactory.CLIENTE_OPTIONAL);
		ScenarioFactory.ATUALIZAR_FULANO.setNome("fulano");
		ScenarioFactory.ATUALIZAR_FULANO.setSobrenome("carvalho");
		when(clienteRepository.findByNomeAndSobrenome(ScenarioFactory.FABIO.getNome(), ScenarioFactory.FABIO.getSobrenome())).thenReturn(ScenarioFactory.CLIENTE_OPTIONAL);
		ClienteDTO clienteDTO=clienteService.atualizarCliente(ScenarioFactory.ATUALIZAR_FULANO);
		assertEquals(ScenarioFactory.ATUALIZAR_FULANO.getNome().toUpperCase(), clienteDTO.getNome());
	}
	
	@Test
	public void atualizarCliente_DadoUmClienteInformado_QuandoClienteNaoExistirNoBanco_EntaoReceboResourceNotFound() {
		
		assertThrows(ResourceNotFoundException.class, () -> clienteService.atualizarCliente(new ClienteAtualizarForm(1,"fulano","tal"))); 
	}
	 
	@Test
	public void atualizarCliente_DadoUmCliente_QuandoTentarAtualizarOClienteComUmNomeJaExistenteNoBanco_EntaoReceboBusinessException() {
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE)).thenReturn(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		when(clienteRepository.save(ScenarioFactory.FABIO)).thenReturn(ScenarioFactory.FABIO);
		ScenarioFactory.ATUALIZAR_FULANO.setId(ScenarioFactory.FABIO.getId());
		ScenarioFactory.ATUALIZAR_FULANO.setNome("FABIO");
		when(clienteRepository.findById(ScenarioFactory.FABIO.getId())).thenReturn(ScenarioFactory.CLIENTE_OPTIONAL);
		when(clienteRepository.findByNomeAndSobrenome(ScenarioFactory.ATUALIZAR_FULANO.getNome(), ScenarioFactory.ATUALIZAR_FULANO.getSobrenome())).thenReturn(ScenarioFactory.CLIENTE_FULANO_OPTIONAL);
		assertThrows(BusinessException.class, () -> clienteService.atualizarCliente(ScenarioFactory.ATUALIZAR_FULANO)); 
		
		
	}
	
	@Test
	public void buscarPorId_DadoUmId_QuandoEstiverUmIdValido_EntaoReceboOk() {
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE)).thenReturn(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		when(clienteRepository.save(ScenarioFactory.FABIO)).thenReturn(ScenarioFactory.FABIO);
		when(clienteRepository.findById(ScenarioFactory.FABIO.getId())).thenReturn(ScenarioFactory.CLIENTE_OPTIONAL);
		ClienteDTO novoCliente = clienteService.buscarPorId(ScenarioFactory.FABIOCARVALHO.getId());
		assertEquals(ScenarioFactory.FABIO.getNome(),novoCliente.getNome());
	}

	@Test
	public void buscarPorId_DadoUmId_QuandoIdNaoEstiverNoBanco_EntaoReceboResourceNotFound() {

		assertThrows(ResourceNotFoundException.class, () -> clienteService.buscarPorId(ScenarioFactory.FABIO.getId()));
	}

	@Test
	public void deletarCliente_DadoUmCliente_QuandoClienteNaoEstiverNoBanco_EntaoReceboResourceNotFound() {

		assertThrows(ResourceNotFoundException.class, () -> clienteService.deletar("JOSE", "CARLOS"));
	}
	
	@Test
	public void deletarCliente_QuandoEstiverNoBanco_EntaoReceboDeletadoComSucesso() {
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE)).thenReturn(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		when(clienteRepository.save(ScenarioFactory.FABIO)).thenReturn(ScenarioFactory.FABIO);
		when(clienteRepository.findByNomeAndSobrenome(ScenarioFactory.FABIO.getNome(),
				ScenarioFactory.FABIO.getSobrenome())).thenReturn(ScenarioFactory.CLIENTE_OPTIONAL);
		String deletado=clienteService.deletar(ScenarioFactory.FABIO.getNome(), ScenarioFactory.FABIO.getSobrenome());
		assertEquals(ScenarioFactory.DELETAR, deletado); 
	}

}
