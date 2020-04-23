package br.com.raia.drogasil.cadastro.testeintegracao;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import br.com.raia.drogasil.cadastro.form.CidadeForm;
import br.com.raia.drogasil.cadastro.model.Cidade;
import br.com.raia.drogasil.cadastro.repository.CidadeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CidadeControllerTeste { 

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CidadeRepository cidadeRepository;
 
	private Cidade canoas;

	private Cidade portoAlegre;

	private CidadeForm cidadeForm;

	private CidadeForm cidadeErro;

	private CidadeForm cidadeJaCadastrada;

	@Before
	public void antes() {
		this.cidadeRepository.deleteAll();

		canoas = new Cidade();
		canoas.setNome("CANOAS");
		canoas.setEstado("RIO GRANDE DO SUL");

		this.cidadeRepository.save(canoas);

		portoAlegre = new Cidade();
		portoAlegre.setNome("PORTO ALEGRE");
		portoAlegre.setEstado("RIO GRANDE DO SUL"); 

		this.cidadeRepository.save(portoAlegre);

		cidadeForm = new CidadeForm();
		cidadeForm.setEstado("Floripa");
		cidadeForm.setNome("Floripa");

		cidadeErro = new CidadeForm();
		cidadeErro.setNome("");
		cidadeErro.setEstado("");

		cidadeJaCadastrada = new CidadeForm();
		cidadeJaCadastrada.setEstado("RIO GRANDE DO SUL");
		cidadeJaCadastrada.setNome("CANOAS");
	}
	
	@After
	public void depois() { 
		this.cidadeRepository.deleteAll();
	}

	@Test
	public void listarTodos() throws Exception {
		mockMvc.perform(get("/cidade").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].nome").value(canoas.getNome()))
				.andExpect(jsonPath("$.[0].estado").value(canoas.getEstado()))
				.andExpect(jsonPath("$.[1].nome").value(portoAlegre.getNome()))
				.andExpect(jsonPath("$.[1].estado").value(portoAlegre.getEstado()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void buscarPorCidadeSucesso() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cidade/nome").param("nome", canoas.getNome()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void buscarPorCidadeError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cidade/nome").param("nome", "C"))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
	}

	@Test
	public void cadastrarUmCidadeComSucesso() throws JsonProcessingException, Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/cidade").content(objectMapper.writeValueAsBytes(cidadeForm))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void cadastrarUmCidadeErro() throws JsonProcessingException, Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/cidade").content(objectMapper.writeValueAsBytes(cidadeErro))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
	}
 
	@Test
	public void cadastrarUmCidadeJaCadastrado() throws JsonProcessingException, Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/cidade").content(objectMapper.writeValueAsBytes(cidadeJaCadastrada))
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError()).andReturn();
	}

	@Test
	public void buscarEstadosSucesso() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cidade/estado").param("estado", canoas.getEstado()))
				.andExpect(jsonPath("$.[0].nome").value(canoas.getNome()))
				.andExpect(jsonPath("$.[0].estado").value(canoas.getEstado()))
				.andExpect(jsonPath("$.[1].nome").value(portoAlegre.getNome()))
				.andExpect(jsonPath("$.[1].estado").value(portoAlegre.getEstado()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	} 

	@Test
	public void buscarEstadosErro() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cidade/estado").param("estado", "PELOTAS"))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
	}
 
	@Test
	public void deletarCidadeComSucesso() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/cidade/deletar").param("nome",portoAlegre.getNome()))
		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	} 
	
	@Test
	public void deletarCidadeSemSucesso() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/cidade/deletar").param("nome","Pelotas"))
		.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
	}

}
