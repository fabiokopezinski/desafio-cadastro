package br.com.raia.drogasil.cadastro.testeintegracao;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.raia.drogasil.cadastro.enumeration.SexoEnum;
import br.com.raia.drogasil.cadastro.form.CidadeForm;
import br.com.raia.drogasil.cadastro.form.ClienteAtualizarForm;
import br.com.raia.drogasil.cadastro.form.ClienteForm;
import br.com.raia.drogasil.cadastro.model.Cidade;
import br.com.raia.drogasil.cadastro.model.Cliente;
import br.com.raia.drogasil.cadastro.repository.CidadeRepository;
import br.com.raia.drogasil.cadastro.repository.ClienteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerTest { 

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	private Cidade canoas;

	private Cliente fabio;
	private Cliente fabioCarvalho;
	private ClienteAtualizarForm atualizarForm;
	private ClienteForm clienteForm;
	private ClienteForm clienteIdade;
	private ClienteForm clienteJaExiste;

	private CidadeForm cidadeForm;

	@Before
	public void antes() {
		this.clienteRepository.deleteAll();
		this.cidadeRepository.deleteAll();

		canoas = new Cidade();
		canoas.setNome("CANOAS");
		canoas.setEstado("RIO GRANDE DO SUL");

		Calendar dataNascimento = Calendar.getInstance();
		dataNascimento.set(Calendar.YEAR, 1993);
		dataNascimento.set(Calendar.MONTH, 10);
		dataNascimento.set(Calendar.DAY_OF_MONTH, 21);
		fabio = new Cliente();
		fabio.setCidade(canoas);
		fabio.setDataNascimento(dataNascimento);
		fabio.setIdade(26);
		fabio.setNome("FABIO");
		fabio.setSobrenome("KOPEZINSKI");
		fabio.setCidade(canoas);
		fabio.setSexo(SexoEnum.MASCULINO);

		fabioCarvalho = new Cliente();
		fabioCarvalho.setCidade(canoas);
		fabioCarvalho.setDataNascimento(dataNascimento);
		fabioCarvalho.setIdade(26);
		fabioCarvalho.setNome("FABIO");
		fabioCarvalho.setSobrenome("CARVALHO");
		fabioCarvalho.setCidade(canoas);
		fabioCarvalho.setSexo(SexoEnum.MASCULINO);

		this.cidadeRepository.save(canoas);
		this.clienteRepository.save(fabio);
		this.clienteRepository.save(fabioCarvalho);

		atualizarForm=new ClienteAtualizarForm();
		atualizarForm.setId(fabio.getId());
		atualizarForm.setNome("SERGIO");
		atualizarForm.setSobrenome("CARVALHO");
		
		cidadeForm = new CidadeForm();
		cidadeForm.setEstado("RIO GRANDE DO SUL");
		cidadeForm.setNome("CANOAS");

		clienteForm = new ClienteForm();
		clienteForm.setNome("FABIO");
		clienteForm.setIdade(27);
		clienteForm.setCidade(cidadeForm);
		clienteForm.setDataNascimento(dataNascimento);
		clienteForm.setSobrenome("CA");
		clienteForm.setSexo(SexoEnum.MASCULINO);

		clienteJaExiste = new ClienteForm();
		clienteJaExiste.setNome("FABIO");
		clienteJaExiste.setIdade(26);
		clienteJaExiste.setCidade(cidadeForm);
		clienteJaExiste.setDataNascimento(dataNascimento);
		clienteJaExiste.setSobrenome("CARVALHO");
		clienteJaExiste.setSexo(SexoEnum.MASCULINO);

		clienteIdade = new ClienteForm();
		clienteIdade.setNome("FABIO");
		clienteIdade.setIdade(26);
		clienteIdade.setCidade(cidadeForm);
		clienteIdade.setDataNascimento(dataNascimento);
		clienteIdade.setSobrenome("C");
		clienteIdade.setSexo(SexoEnum.MASCULINO);

	}

	@After
	public void depois() {
		this.clienteRepository.deleteAll();
		this.cidadeRepository.deleteAll();

	}

	@Test
	public void listarTodos() throws Exception {
		mockMvc.perform(get("/cliente").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].nome").value(fabio.getNome()))
				.andExpect(jsonPath("$.[0].sobrenome").value(fabio.getSobrenome()))
				.andExpect(jsonPath("$.[1].nome").value(fabioCarvalho.getNome()))
				.andExpect(jsonPath("$.[1].sobrenome").value(fabioCarvalho.getSobrenome()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void buscarPorIdSucesso() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/" + fabio.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void buscarPorIdErro() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/100"))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
	}

	@Test
	public void cadastrarUmClienteComSucesso() throws JsonProcessingException, Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/cliente").content(objectMapper.writeValueAsBytes(clienteForm))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void cadastrarUmClienteComErroIdade() throws JsonProcessingException, Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/cliente").content(objectMapper.writeValueAsBytes(clienteIdade))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError()).andReturn();
	}

	@Test
	public void cadastrarUmClienteJaExistente() throws JsonProcessingException, Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/cliente").content(objectMapper.writeValueAsBytes(clienteJaExiste))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError()).andReturn();
	}

	@Test
	public void buscarPorNomeSucesso() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/nome").param("nome", fabio.getNome()))
		.andExpect(jsonPath("$.[0].nome").value(fabio.getNome()))
		.andExpect(jsonPath("$.[0].sobrenome").value(fabio.getSobrenome()))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test
	public void buscarPorNomeErro() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/nome").param("nome", "Fá"))
		.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
	}

	@Test
	public void buscarPorNomeCompletoSucesso() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/nome").param("nome", "FABIO").param("sobrenome", "KOPEZINSKI"))
		.andExpect(jsonPath("$.[0].nome").value(fabio.getNome()))
		.andExpect(jsonPath("$.[0].sobrenome").value(fabio.getSobrenome()))
		.andExpect(jsonPath("$.[1].nome").value(fabioCarvalho.getNome()))
		.andExpect(jsonPath("$.[1].sobrenome").value(fabioCarvalho.getSobrenome()))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	@Test
	public void buscarPorNomeCompletoErro() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/nome").param("nome", "FABIOJ").param("sobrenome", "G"))
		.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
	}
	

	@Test
	public void deletarClienteComSucesso() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/cliente/deletar").param("nome",fabio.getNome()).param("sobrenome", fabio.getSobrenome()))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test
	public void deletarClienteSemSucesso() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/cliente/deletar").param("nome","KAKA").param("sobrenome", "KA"))
		.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
	}
	
	
	
	
	
	

}
