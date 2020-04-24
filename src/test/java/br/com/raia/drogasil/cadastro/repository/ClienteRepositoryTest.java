package br.com.raia.drogasil.cadastro.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.raia.drogasil.cadastro.enumeration.SexoEnum;
import br.com.raia.drogasil.cadastro.model.Cidade;
import br.com.raia.drogasil.cadastro.model.Cliente;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ClienteRepositoryTest {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Rule
	public ExpectedException thrown= ExpectedException.none(); 
	
	private Cliente fabio;
	
	private Cidade portoAlegre;
	
	
	private List<Cliente> listaDeCliente= new ArrayList<Cliente>();
	
	@Before
	public void antes() {
		this.clienteRepository.deleteAll();
		this.cidadeRepository.deleteAll();
		portoAlegre=new Cidade();
		portoAlegre.setNome("PORTO ALEGRE");
		portoAlegre.setEstado("RIO GRANDE DO SUL");
		cidadeRepository.save(portoAlegre);
		fabio=new Cliente();
		
		LocalDate dataNascimento=LocalDate.of(1993, 10, 21);
		
		fabio.setCidade(portoAlegre);
		fabio.setDataNascimento(dataNascimento);
		fabio.setIdade(26);
		fabio.setNome("FABIO");
		fabio.setSobrenome("KOPEZINSKI");
		fabio.setCidade(portoAlegre);
		fabio.setSexo(SexoEnum.MASCULINO);
		clienteRepository.save(fabio);
		listaDeCliente.add(fabio);
	}
	
	@After
	public void depois()
	{
		this.clienteRepository.deleteAll();
		this.cidadeRepository.deleteAll();
	}
	
	@Test
	public void salvar() {
		assertThat("FABIO").isEqualTo(fabio.getNome());
		assertThat("KOPEZINSKI").isEqualTo(fabio.getSobrenome());
	}
	
	@Test
	public void buscarPorNomeCompleto() {
		Optional<Cliente> cliente=clienteRepository.findByNomeAndSobrenome("FABIO", "KOPEZINSKI");
		assertThat(cliente.get().getNome()).isEqualTo(fabio.getNome());
		assertThat(cliente.get().getSobrenome()).isEqualTo(fabio.getSobrenome());
	}
	
	@Test
	public void buscarPorNomes() {
		List<Cliente> nomes=clienteRepository.findByNome("FABIO");
		assertThat(nomes.get(0).getNome()).isEqualTo(listaDeCliente.get(0).getNome());
	}

}
