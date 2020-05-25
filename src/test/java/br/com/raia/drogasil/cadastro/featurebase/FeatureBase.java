package br.com.raia.drogasil.cadastro.featurebase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.raia.drogasil.cadastro.domain.dto.CidadeDTO;
import br.com.raia.drogasil.cadastro.domain.dto.ClienteDTO;
import br.com.raia.drogasil.cadastro.domain.form.CidadeForm;
import br.com.raia.drogasil.cadastro.domain.form.ClienteForm;


public class FeatureBase {

	@Autowired
	private RestTemplate template;
	@LocalServerPort
	int randomServerPort;

	protected ResponseEntity<List<CidadeDTO>> listaDeCidades() {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		ResponseEntity<List<CidadeDTO>> exchange = template.exchange(
				"http://localhost:" + randomServerPort + "/cidades", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CidadeDTO>>() { 
				});
		return exchange;
	}
	
	protected ResponseEntity<ClienteDTO> cadastrar(ClienteForm form) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		HttpEntity<ClienteForm> request = new HttpEntity<>(form, headers);
		return template.postForEntity("http://localhost:" + randomServerPort + "/clientes", request, ClienteDTO.class);
	}
	
	protected ResponseEntity<CidadeDTO> cadastrarCidade(CidadeForm form) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		HttpEntity<CidadeForm> request = new HttpEntity<>(form, headers);
		return template.postForEntity("http://localhost:" + randomServerPort + "/cidades", request, CidadeDTO.class);
	}
	
	protected ResponseEntity<ClienteDTO> buscarPorNomeCompleto(String nome,String sobreNome) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		HttpEntity<String> body = new HttpEntity<>(nome+""+sobreNome, headers);
		return template.exchange("http://localhost:" + randomServerPort + "/clientes/nomecompleto?nome="+nome+"&sobrenome="+sobreNome ,
				HttpMethod.GET, body, ClienteDTO.class);
	}
	
	protected ResponseEntity<List<ClienteDTO>> listarClientes() {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		ResponseEntity<List<ClienteDTO>> exchange = template.exchange(
				"http://localhost:" + randomServerPort + "/clientes", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ClienteDTO>>() {
				});
		return exchange;
	}

	

	protected ResponseEntity<CidadeDTO> buscarPorCidade(String cidade) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		HttpEntity<String> body = new HttpEntity<>(cidade, headers);
		return template.exchange("http://localhost:" + randomServerPort + "/cidades/nome?nome=" + cidade,
				HttpMethod.GET, body, CidadeDTO.class);
	}

	protected ResponseEntity<List<CidadeDTO>> buscarPorEstado(String estado) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		ResponseEntity<List<CidadeDTO>> exchange = template.exchange(
				"http://localhost:" + randomServerPort + "/cidades/estado?estado=" + estado, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CidadeDTO>>() {
				});
		return exchange;
	}

}
