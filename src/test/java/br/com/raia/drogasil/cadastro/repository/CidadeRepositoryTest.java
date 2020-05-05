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

import br.com.raia.drogasil.cadastro.domain.model.Cidade;
import br.com.raia.drogasil.cadastro.domain.repository.CidadeRepository;
import br.com.raia.drogasil.cadastro.scenario.ScenarioFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CidadeRepositoryTest {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private List<Cidade> cidadesLista = new ArrayList<Cidade>();

	@Before
	public void antes() {

		cidadesLista.add(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		this.cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE);
	}

	@After
	public void depois() {
		this.cidadeRepository.deleteAll();
	}

	@Test
	public void buscarPorNome() {
		Optional<Cidade> cidade = cidadeRepository.findByNome(ScenarioFactory.CIDADE_PORTO_ALEGRE.getNome());
		assertThat(cidade.get().getNome()).isEqualTo(ScenarioFactory.CIDADE_PORTO_ALEGRE.getNome());
	}

	@Test
	public void buscarPorEstado() {
		Optional<List<Cidade>> cidades = cidadeRepository.findByEstado(ScenarioFactory.RIO_GRANDE_DO_SUL);
		assertThat(cidades.get().get(0).getEstado()).isEqualTo(cidadesLista.get(0).getEstado());
	}

}
