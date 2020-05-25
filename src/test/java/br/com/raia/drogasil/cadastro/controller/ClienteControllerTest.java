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

import br.com.raia.drogasil.cadastro.domain.dto.ClienteDTO;
import br.com.raia.drogasil.cadastro.repository.CidadeRepository;
import br.com.raia.drogasil.cadastro.repository.ClienteRepository;
import br.com.raia.drogasil.cadastro.scenario.ScenarioFactory;
import br.com.raia.drogasil.cadastro.service.ClienteService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClienteControllerTest {

	@MockBean
	private CidadeRepository cidadeRepository;

	@MockBean
	private ClienteRepository clienteRepository;

	@MockBean
	private ClienteService clienteService;

	@Autowired
	private ClienteController clienteController;

	private List<ClienteDTO> listarCliente = new ArrayList<ClienteDTO>();

	
	@Test
	public void listarClientes_DadoUmaChamadaAlistarClientes_QuandoEstiverPopulado_EntaoReceboUmaListaDeClientes(){
		listarCliente.add(ScenarioFactory.FABIO_DTO);
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE)).thenReturn(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		when(clienteRepository.save(ScenarioFactory.FABIO)).thenReturn(ScenarioFactory.FABIO);
		when(clienteService.listarClientes()).thenReturn(listarCliente);
		List<ClienteDTO> listaCliente = clienteController.listarClientes();
		assertEquals(1, listaCliente.size());
		verify(clienteService).listarClientes();

	} 

	@Test

	public void buscarPorId_DadoUmId_QuandoProcurarEAcharEsseCliente_EntaoReceboOk() throws Exception {
		when(cidadeRepository.save(ScenarioFactory.RIO_GRANDE)).thenReturn(ScenarioFactory.RIO_GRANDE);
		when(clienteRepository.save(ScenarioFactory.BELTRANO)).thenReturn(ScenarioFactory.BELTRANO);
		when(clienteService.buscarPorId(ScenarioFactory.BELTRANO.getId())).thenReturn(ScenarioFactory.BELTRANO_DTO);
		ClienteDTO cliente = clienteController.buscarPorId(ScenarioFactory.BELTRANO.getId());
		assertThat(cliente.getNome()).isEqualTo(ScenarioFactory.BELTRANO.getNome());
		assertThat(cliente.getSobrenome()).isEqualTo(ScenarioFactory.BELTRANO.getSobrenome());
		assertThat(cliente.getSexo()).isEqualTo(ScenarioFactory.BELTRANO.getSexo());
		assertThat(cliente.getDataNascimento()).isEqualTo(ScenarioFactory.BELTRANO.getDataNascimento());
		assertThat(cliente.getCidade().getNome()).isEqualTo(ScenarioFactory.BELTRANO.getCidade().getNome());
		assertThat(cliente.getCidade().getEstado()).isEqualTo(ScenarioFactory.BELTRANO.getCidade().getEstado());
		
		verify(clienteService).buscarPorId(ScenarioFactory.BELTRANO.getId());
	}

	@Test
	
	public void cadastrar_DadoUmNovoCliente_QuandoOClienteEstiverDeAcordoComARegraDeNegocio_EntaoReceboOk() throws Exception {
		when(cidadeRepository.save(ScenarioFactory.CIDADE_VIAMAO)).thenReturn(ScenarioFactory.CIDADE_VIAMAO);
		when(clienteService.cadastrar(ScenarioFactory.CLIENTE_NOVO_FULANO)).thenReturn(ScenarioFactory.CLIENTE_NOVO_FULANO_DTO);
		ClienteDTO cliente = clienteController.cadastrar(ScenarioFactory.CLIENTE_NOVO_FULANO);
		assertThat(cliente.getNome()).isEqualTo(ScenarioFactory.CLIENTE_NOVO_FULANO.getNome());
		assertThat(cliente.getSobrenome()).isEqualTo(ScenarioFactory.CLIENTE_NOVO_FULANO.getSobrenome());
		assertThat(cliente.getSexo()).isEqualTo(ScenarioFactory.CLIENTE_NOVO_FULANO.getSexo());
		assertThat(cliente.getDataNascimento()).isEqualTo(ScenarioFactory.CLIENTE_NOVO_FULANO.getDataNascimento());
		verify(clienteService).cadastrar(ScenarioFactory.CLIENTE_NOVO_FULANO);
	}
	
	@Test
	
	public void atualizarCliente_DadoUmCliente_QuandoAtualizar_EntaoReceboOK() {
		when(cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE)).thenReturn(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		ScenarioFactory.ATUALIZAR_FULANO.setId(ScenarioFactory.FABIO.getId());
		ScenarioFactory.ATUALIZAR_FULANO.setNome("FULANO");
		ScenarioFactory.ATUALIZAR_FULANO.setSobrenome(ScenarioFactory.FABIO.getSobrenome());
		ScenarioFactory.FABIO_DTO.setNome("FULANO");
		when(clienteService.atualizarCliente(ScenarioFactory.ATUALIZAR_FULANO)).thenReturn(ScenarioFactory.FABIO_DTO);
		ClienteDTO clienteDTO = clienteController.atualizarCliente(ScenarioFactory.ATUALIZAR_FULANO);
		assertEquals(ScenarioFactory.ATUALIZAR_FULANO.getNome(), clienteDTO.getNome());		
	}

	@Test
	
	public void buscarNomeESobrenome_DadoUmNomeESobrenomeDeUmCliente_QuandoOsParametrosEstiveremDeAcordoComARegraDeNegocio_EntaoReceboOCliente() {
		when(cidadeRepository.save(ScenarioFactory.RIO_GRANDE)).thenReturn(ScenarioFactory.RIO_GRANDE);
		when(clienteRepository.save(ScenarioFactory.BELTRANO)).thenReturn(ScenarioFactory.BELTRANO);
		when(clienteService.buscarNomeESobrenome(ScenarioFactory.BELTRANO.getNome(), ScenarioFactory.BELTRANO.getSobrenome()))
				.thenReturn(ScenarioFactory.BELTRANO_DTO);
		ClienteDTO cliente = clienteController.buscarPorNomeCompleto(ScenarioFactory.BELTRANO.getNome(),
				ScenarioFactory.BELTRANO.getSobrenome());
		assertThat(cliente.getNome()).isEqualTo(ScenarioFactory.BELTRANO.getNome());
		assertThat(cliente.getSobrenome()).isEqualTo(ScenarioFactory.BELTRANO.getSobrenome());
		assertThat(cliente.getSexo()).isEqualTo(ScenarioFactory.BELTRANO.getSexo());
		assertThat(cliente.getDataNascimento()).isEqualTo(ScenarioFactory.BELTRANO.getDataNascimento());
		assertThat(cliente.getCidade().getNome()).isEqualTo(ScenarioFactory.BELTRANO.getCidade().getNome());
		assertThat(cliente.getCidade().getEstado()).isEqualTo(ScenarioFactory.BELTRANO.getCidade().getEstado());
	}

	@Test
	
	public void buscarPorNome_DadoUmNomeInformado_QuandoEstiverDeAcordoComARegraDeNegocio_EntaoReceboOk() {
		listarCliente.add(ScenarioFactory.BELTRANO_DTO);
		when(cidadeRepository.save(ScenarioFactory.RIO_GRANDE)).thenReturn(ScenarioFactory.RIO_GRANDE);
		when(clienteRepository.save(ScenarioFactory.BELTRANO)).thenReturn(ScenarioFactory.BELTRANO);
		when(clienteService.buscarPorNome(ScenarioFactory.BELTRANO.getNome())).thenReturn(listarCliente);
		List<ClienteDTO> listaCliente = clienteController.buscarPorNome(ScenarioFactory.BELTRANO.getNome());
		assertThat(listaCliente.get(0).getNome()).isEqualTo(ScenarioFactory.BELTRANO.getNome());
		assertThat(listaCliente.get(0).getSobrenome()).isEqualTo(ScenarioFactory.BELTRANO.getSobrenome());
		assertThat(listaCliente.get(0).getSexo()).isEqualTo(ScenarioFactory.BELTRANO.getSexo());
		assertThat(listaCliente.get(0).getDataNascimento()).isEqualTo(ScenarioFactory.BELTRANO.getDataNascimento());
		assertThat(listaCliente.get(0).getCidade().getNome()).isEqualTo(ScenarioFactory.BELTRANO.getCidade().getNome());
		assertThat(listaCliente.get(0).getCidade().getEstado())
				.isEqualTo(ScenarioFactory.BELTRANO.getCidade().getEstado());
	}

	@Test
	
	public void deletarCliente_QuandoEstiverNoBanco_EntaoReceboDeletadoComSucesso() throws Exception {
		when(cidadeRepository.save(ScenarioFactory.RIO_GRANDE)).thenReturn(ScenarioFactory.RIO_GRANDE);
		when(clienteRepository.save(ScenarioFactory.BELTRANO)).thenReturn(ScenarioFactory.BELTRANO);
		when(clienteService.deletar(ScenarioFactory.BELTRANO.getNome(), ScenarioFactory.BELTRANO.getSobrenome()))
				.thenReturn(ScenarioFactory.DELETAR);
		String delete = clienteController.deletarCliente(ScenarioFactory.BELTRANO.getNome(),
				ScenarioFactory.BELTRANO.getSobrenome());
		assertEquals(ScenarioFactory.DELETAR, delete);
	}

	@Test
	
	public void deletarCliente_DadoUmCliente_QuandoClienteNaoEstiverNoBanco_EntaoReceboResourceNotFound() throws Exception {
		when(clienteService.deletar("FULANO", "TAL")).thenReturn(ScenarioFactory.NAO_FOI_ENCONTRADO);
		String delete = clienteController.deletarCliente("FULANO", "TAL");
		assertEquals(ScenarioFactory.NAO_FOI_ENCONTRADO, delete);
	}

}
