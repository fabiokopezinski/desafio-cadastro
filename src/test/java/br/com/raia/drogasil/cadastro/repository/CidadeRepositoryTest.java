package br.com.raia.drogasil.cadastro.repository;

import static org.assertj.core.api.Assertions.assertThat;

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

import br.com.raia.drogasil.cadastro.model.Cidade;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CidadeRepositoryTest { 

	@Autowired
	private CidadeRepository cidadeRepository;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private Cidade portoAlegre;
	
	private List<Cidade> cidadesLista= new ArrayList<Cidade>();

	@Before
	public void antes() {

		this.cidadeRepository.deleteAll();
		portoAlegre = new Cidade(); 
		portoAlegre.setEstado("RIO GRANDE DO SUL");
		portoAlegre.setNome("PORTO ALEGRE"); 
		cidadesLista.add(portoAlegre);
		cidadeRepository.save(portoAlegre); 
	}

	@After
	public void depois() {
		this.cidadeRepository.deleteAll();
	}

	@Test
	public void salvar() {

		assertThat("RIO GRANDE DO SUL").isEqualTo(portoAlegre.getEstado());
		assertThat("PORTO ALEGRE").isEqualTo(portoAlegre.getNome());
	}
	
	@Test
	public void buscarPorNome() {
		Optional<Cidade> cidade= cidadeRepository.findByNome(portoAlegre.getNome()); 
		assertThat(cidade.get().getNome()).isEqualTo(portoAlegre.getNome());
	}
	
	@Test
	public void buscarPorEstado()
	{
		List<Cidade> cidades= cidadeRepository.findByEstado("RIO GRANDE DO SUL");
		assertThat(cidades.get(0).getEstado()).isEqualTo(cidadesLista.get(0).getEstado());
	}

}
