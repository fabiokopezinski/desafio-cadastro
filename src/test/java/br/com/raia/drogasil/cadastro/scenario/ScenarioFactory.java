package br.com.raia.drogasil.cadastro.scenario;

import java.time.LocalDate;

import br.com.raia.drogasil.cadastro.domain.dto.CidadeDTO;
import br.com.raia.drogasil.cadastro.domain.enumeration.SexoEnum;
import br.com.raia.drogasil.cadastro.domain.form.CidadeForm;
import br.com.raia.drogasil.cadastro.domain.form.ClienteAtualizarForm;
import br.com.raia.drogasil.cadastro.domain.form.ClienteForm;
import br.com.raia.drogasil.cadastro.domain.model.Cidade;
import br.com.raia.drogasil.cadastro.domain.model.Cliente;

public class ScenarioFactory {
 
	public static Cidade portoAlegre;

	public static final String RIO_GRANDE_DO_SUL = "RIO GRANDE DO SUL";
	public static final String SAO_PAULO = "SAO PAULO"; 
	public static final String PORTO_ALEGRE = "PORTO ALEGRE";
	public static final String PELOTAS = "PELOTAS";
	public static final Cidade CIDADE_PORTO_ALEGRE = cidade();  
	public static final Cidade CIDADE_PASSO_FUNDO = cidadeDois();
	public static final CidadeForm CIDADE_GRAVATAI=gravatai();
	public static final CidadeForm CIDADE_Form = cidadeForm();
	public static final ClienteForm CLIENTE_FORM_EXISTENTE = clienteJaExiste();
	public static final ClienteForm NOVO_CLIENTE= clienteNovo();
	public static final CidadeForm CIDADE_ERRO = cidadeErro();
	public static final CidadeForm ESTADO_NAO_EXISTE = estadoNaoExisteErro();
	public static final CidadeForm CIDADE_JAEXISTE = jaExiste();
	public static final CidadeDTO CIDADEDTO = cidadeDTO();
	public static final ClienteForm CLIENTE_NOVO_FULANO = clienteFulano();
	public static final ClienteForm CLIENTE_NOVO_BELTRANO= clienteBeltrano();
	public static final ClienteForm CLIENTE_JA_EXISTE= clienteExistente();
	public static final ClienteAtualizarForm ATUALIZAR_FULANO=atualizarFulano();
	public static final Cliente FABIO = cliente();
	public static final Cliente FABIOCARVALHO = clienteDois();
	public static final Cliente FULANO = fulano(); 
	public static final CidadeForm ALEGRETE=cidadeAlegreForm(); 


	public static final String NAO_ACHOU_NENHUM_ESTADO = "Não achou nenhum estado";
	public static final String JA_FOI_CADASTRO = "Ja foi cadastrado";
	public static final String DELETAR = "Deletada com sucesso";
	public static final String NAO_FOI_ENCONTRADO = "Não foi encontrado";

	public static final String CLIENTES_NAO_FORAM_ENCONTRADOS = "Clientes com esse nome não foram encontrados";
	public static final String CLIENTE_NAO_FOI_ENCONTRADO = "Cliente não foi encontrado";
	public static final String NAO_ACHOU = "Não achou o ID informado";

	public static final String JA_FOI_REGISTRADO = "Cliente já foi cadastrado";
	public static final String JA_EXISTE = "Já existe alguém com esse nome";

	private static Cidade cidade() {

		portoAlegre = new Cidade();
		portoAlegre.setNome("PORTO ALEGRE");
		portoAlegre.setEstado("RIO GRANDE DO SUL");
		return portoAlegre;
	}

	private static ClienteAtualizarForm atualizarFulano() {
		ClienteAtualizarForm atualizar=new ClienteAtualizarForm(null, null, null);
		return atualizar;
	}

	private static ClienteForm clienteExistente() {
		LocalDate dataNascimento = LocalDate.of(1993, 10, 21);
		return new ClienteForm("FULANO", "TAL", SexoEnum.MASCULINO, dataNascimento, CIDADE_Form);
	}

	private static ClienteForm clienteNovo() {
		LocalDate dataNascimento = LocalDate.of(1993, 10, 21);
		return new ClienteForm("FULANO", "CARVALHO", SexoEnum.MASCULINO, dataNascimento, null);
	}

	private static ClienteForm clienteBeltrano() {
		LocalDate dataNascimento = LocalDate.of(1993, 10, 21);
		return new ClienteForm("BELTRANO", "KOPEZINSKI", SexoEnum.MASCULINO, dataNascimento, CIDADE_Form);
	}

	private static Cliente fulano() {
		LocalDate dataNascimento = LocalDate.of(1993, 10, 21);
		Cliente cliente = new Cliente();
		cliente.setNome("FULANO"); 
		cliente.setSobrenome("TALL");
		cliente.setSexo(SexoEnum.MASCULINO);
		cliente.setIdade(26);
		cliente.setDataNascimento(dataNascimento);
		cliente.setCidade(alegrete());
		return cliente;
	}

	private static ClienteForm clienteJaExiste() {
		LocalDate dataNascimento = LocalDate.of(1993, 10, 21);
		return new ClienteForm("FABIO", "KOPEZINSKI", SexoEnum.MASCULINO, dataNascimento, CIDADE_GRAVATAI);
	}

	private static Cidade cidadeDois() {
		Cidade passoFundo = new Cidade();
		passoFundo.setNome("PASSO FUNDO");
		passoFundo.setEstado("RIO GRANDE DO SUL");
		return passoFundo;

	}

	private static Cliente cliente() {

		LocalDate dataNascimento = LocalDate.of(1993, 10, 21);
		Cliente cliente = new Cliente();
		cliente.setNome("FABIO");
		cliente.setSobrenome("KOPEZINSKI");
		cliente.setSexo(SexoEnum.MASCULINO);
		cliente.setIdade(26);
		cliente.setDataNascimento(dataNascimento);
		cliente.setCidade(CIDADE_PORTO_ALEGRE);
		return cliente;
	}

	private static Cliente clienteDois() {
		LocalDate dataNascimento = LocalDate.of(1993, 10, 21);
		Cliente cliente = new Cliente();
		cliente.setNome("FABIO");
		cliente.setSobrenome("CARVALHO");
		cliente.setSexo(SexoEnum.MASCULINO);
		cliente.setIdade(26);
		cliente.setDataNascimento(dataNascimento);
		cliente.setCidade(CIDADE_PASSO_FUNDO);
		return cliente;
	}

	private static ClienteForm clienteFulano() {
		LocalDate dataNascimento = LocalDate.of(1993, 10, 21);
		ClienteForm cliente = new ClienteForm("FULANO", "TAL", SexoEnum.MASCULINO, dataNascimento, CIDADE_Form); 
		return cliente;
	} 
 
	private static CidadeForm cidadeForm() { 

		CidadeForm cidade = new CidadeForm("VIAMAO", "RIO GRANDE DO SUL");

		return cidade;
	}
	
	private static CidadeForm cidadeAlegreForm() {

		CidadeForm cidade = new CidadeForm("ALEGRETE", "RIO GRANDE DO SUL");

		return cidade;
	}
	
	
	private static CidadeForm gravatai() {

		CidadeForm cidade = new CidadeForm("GRAVATAI", "RIO GRANDE DO SUL");

		return cidade;
	}
	
	

	private static CidadeForm estadoNaoExisteErro() {
		return new CidadeForm("FLORIPA", "SANTA CATARINA");
	} 

	private static CidadeForm jaExiste() {

		return new CidadeForm("PORTO ALEGRE", "RIO GRANDE DO SUL");
	}

	private static CidadeForm cidadeErro() {

		return new CidadeForm("PASSO FUNDO", "");
	}

	private static CidadeDTO cidadeDTO() {

		return new CidadeDTO("PORTO ALEGRE", "RIO GRANDE DO SUL");
	}
	
	private static Cidade alegrete() {
		Cidade alegrete = new Cidade();
		alegrete.setNome("ALEGRETE");
		alegrete.setEstado("RIO GRANDE DO SUL");
		return alegrete;
	}

}
