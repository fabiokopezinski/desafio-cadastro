package br.com.raia.drogasil.cadastro.testeunitario.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import br.com.raia.drogasil.cadastro.model.Cidade;
import br.com.raia.drogasil.cadastro.repository.CidadeRepository;
import br.com.raia.drogasil.cadastro.service.CidadeService;

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
	
	private List<Cidade> listaCidades= new ArrayList<Cidade>();
	
	private Cidade portoAlegre;
	private Cidade passoFundo;
	
	private Cidade pelotas;
	
	@Before
	public void antes() {
		
		portoAlegre=new Cidade();
		portoAlegre.setNome("PORTO ALEGRE");
		portoAlegre.setEstado("RIO GRANDE DO SUL");
		
		passoFundo=new Cidade();
		passoFundo.setNome("PASSO FUNDO");
		passoFundo.setEstado("RIO GRANDE DO SUL");
		
		pelotas=new Cidade();
		pelotas.setId(250);
		pelotas.setNome("PELOTAS");
		pelotas.setEstado("RIO GRANDE DO SUL");
		
		listaCidades.add(portoAlegre);
		listaCidades.add(passoFundo);
		
		cidadeRepository.save(portoAlegre);
		cidadeRepository.save(passoFundo);

		
	}
	
	@Test
	public void listaDeCidades() {
		when(cidadeRepository.findAll()).thenReturn(listaCidades);
		List<Cidade> listaCidadeService= cidadeService.listaDeCidades();
		assertThat(listaCidadeService.get(0).getNome()).isEqualTo("PORTO ALEGRE");
		assertThat(listaCidadeService.get(1).getNome()).isEqualTo("PASSO FUNDO");
	}
	
		
	
	@Test
	public void buscarPorNomeComSucesso() {
		Optional<Cidade> cidade= Optional.empty();
		cidade=Optional.of(portoAlegre);
		when(cidadeRepository.findByNome("PORTO ALEGRE")).thenReturn(cidade);
		Cidade novaCidade= cidadeService.buscarPorCidade(portoAlegre.getNome());
		assertThat(novaCidade.getNome()).isEqualTo("PORTO ALEGRE"); 
	}
	
	@Test
	public void buscarPorNomeSemSucesso() throws Exception {
		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage("Nome da cidade não encontrada");
		cidadeService.buscarPorCidade("PELOTAS");
		
	}
	
	@Test
	public void buscarPorEstadoComSucesso() throws Exception {
		
		when(cidadeRepository.findByEstado("RIO GRANDE DO SUL")).thenReturn(listaCidades);
		List<Cidade>novaListaCidades= cidadeService.buscarPorEstado("RIO GRANDE DO SUL");
		assertThat(novaListaCidades.get(0).getEstado()).isEqualTo("RIO GRANDE DO SUL");
		assertThat(novaListaCidades.get(1).getEstado()).isEqualTo("RIO GRANDE DO SUL"); 
	} 
	
	@Test
	public void buscarPorEstadoSemSucesso() throws Exception {
		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage("Não achou nenhum estado");
		cidadeService.buscarPorEstado("SAO PAULO");   
	} 
	
	@Test
	public void deletarErro() throws Exception
	{
		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage("Não foi encontrado"); 
		cidadeService.deletar("SAO PAULO");   
	}
		
	
}
