package br.com.raia.drogasil.cadastro.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.raia.drogasil.cadastro.domain.model.Cliente;
import br.com.raia.drogasil.cadastro.domain.repository.CidadeRepository;
import br.com.raia.drogasil.cadastro.domain.repository.ClienteRepository;
import br.com.raia.drogasil.cadastro.scenario.ScenarioFactory;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClienteRepositoryTest {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void antes() {

		this.cidadeRepository.save(ScenarioFactory.CIDADE_PORTO_ALEGRE);
		this.cidadeRepository.save(ScenarioFactory.CIDADE_PASSO_FUNDO);
		this.clienteRepository.save(ScenarioFactory.FABIO);
		this.clienteRepository.save(ScenarioFactory.FABIOCARVALHO);
	}

	@After
	public void depois() {
		this.cidadeRepository.deleteAll();
		this.clienteRepository.deleteAll();
		

	}

	@Test
	public void buscarPorNomeCompleto() {
		Optional<Cliente> cliente = clienteRepository.findByNomeAndSobrenome(ScenarioFactory.FABIO.getNome(),
				ScenarioFactory.FABIO.getSobrenome());
		System.out.println(cliente.toString());
		assertThat(cliente.get().getNome()).isEqualTo(ScenarioFactory.FABIO.getNome());
		assertThat(cliente.get().getSobrenome()).isEqualTo(ScenarioFactory.FABIO.getSobrenome());
	}

}
