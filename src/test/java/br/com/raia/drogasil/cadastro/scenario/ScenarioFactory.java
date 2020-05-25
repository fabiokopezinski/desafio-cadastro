package br.com.raia.drogasil.cadastro.scenario;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import br.com.raia.drogasil.cadastro.domain.dto.CidadeDTO;
import br.com.raia.drogasil.cadastro.domain.dto.ClienteDTO;
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
	public static final String PORTO_STRINGGRE = "PORTO ALEGRE";
	public static final String PELOTAS = "PELOTAS";

	public static final CidadeForm CIDADE_GRAVATAI = gravatai(); 
	public static final CidadeForm CIDADE_Form = cidadeForm();
	public static final CidadeForm CIDADE_ERRO = cidadeErro();
	public static final CidadeForm ESTADO_NAO_EXISTE = estadoNaoExisteErro();
	public static final CidadeForm CIDADE_JAEXISTE = jaExiste();
	public static final CidadeForm RIO_GRANDE_Form = cidadeRioGrandeForm();
	public static final CidadeDTO CIDADEDTO = cidadeDTO();
	public static final CidadeDTO CIDADE_VIAMAO_DTO=cidadeDTOViamao();
	//public static final Cidade CIDADE_ALEGRETE = alegrete();
	public static final Cidade RIO_GRANDE = cidadeRioGrande();
	public static final Cidade CIDADE_PORTO_ALEGRE = cidade();
	public static final CidadeDTO CIDADE_PORTO_ALEGRE_DTO = cidadeDTO();
	public static final Cidade CIDADE_PASSO_FUNDO = cidadeDois();
	public static final Cidade CIDADE_VIAMAO = cidadeTres();
	public static final Optional<Cidade> CIDADE_OPTIONAL=cidadeOP();
	public static final Optional<CidadeDTO>CIDADE_OPTIONAL_DTO=cidadeDTOOp();
	public static final Optional<List<CidadeDTO>> CIDADE_OPTIONAL_DTO_LISTA=cidadeDTOListOp();
	public static final Optional<List<Cidade>> CIDADE_OPTIONAL_LIST=cidadeOpList();

	public static final ClienteForm CLIENTE_NOVO_FULANO = clienteFulano();
	public static final ClienteForm CLIENTE_NOVO_BELTRANO = clienteBeltrano();
	public static final ClienteForm CLIENTE_JA_EXISTE = clienteExistente();
	public static final ClienteForm CLIENTE_FORM_EXISTENTE = clienteJaExiste();
	public static final ClienteForm NOVO_CLIENTE = clienteNovo();
	public static final ClienteAtualizarForm ATUALIZAR_FULANO = atualizarFulano();
	public static final ClienteAtualizarForm CLIENTE_EXISTENTE = clienteExiste();
	public static final Cliente FABIO = cliente();
	public static final ClienteDTO FABIO_DTO=clienteFABIODTO();
	public static final Cliente FABIOCARVALHO = clienteDois();
	public static final Cliente FABIOKOPEZINSKI = clienteTres();
	public static final Cliente FULANO = fulano();
	public static final Optional<Cliente> CLIENTE_FULANO_OPTIONAL=optionalFulano();
	public static final ClienteDTO CLIENTE_NOVO_FULANO_DTO=clienteNovoFulanoDTO();
	public static final Optional<ClienteDTO> CLIENTE_FULANO_OPTIONAL_DTO=optionalFulanoDTO();
	public static final Cliente FULANOTAL=fulanoTal();
	public static final Cliente BELTRANO = beltrano();
	public static final ClienteDTO BELTRANO_DTO=clienteBELTRANODTO();
	public static final Optional<Cliente> CLIENTE_OPTIONAL=clienteOp();
	public static final Optional<List<Cliente>>CLIENTE_OPTIONAL_LIST=clienteList();

	
	public static final String DELETAR = "Deletada com sucesso";
	public static final String NAO_FOI_ENCONTRADO = "Não foi encontrado";

	

	private static Cidade cidade() {

		portoAlegre = new Cidade();
		portoAlegre.setNome("PORTO ALEGRE");
		portoAlegre.setEstado("RIO GRANDE DO SUL");
		return portoAlegre;
	}

	private static ClienteDTO clienteNovoFulanoDTO() {
		LocalDate dataNascimento = LocalDate.of(1993, 10, 21);
		ClienteDTO fulano=new ClienteDTO();
		fulano.setCidade(CIDADE_VIAMAO_DTO);
		fulano.setDataNascimento(dataNascimento);
		fulano.setNome("fulano");
		fulano.setSobrenome("tal");
		fulano.setSexo(SexoEnum.MASCULINO);
		return fulano;
	}

	private static Optional<ClienteDTO> optionalFulanoDTO() {
		Optional<ClienteDTO> cliente = Optional.empty();
		cliente = Optional.of(CLIENTE_NOVO_FULANO_DTO);
		return cliente;
	}

	private static ClienteDTO clienteFABIODTO() {
		ClienteDTO fabio=new ClienteDTO();
		fabio.setCidade(CIDADE_PORTO_ALEGRE_DTO);
		fabio.setDataNascimento(FABIO.getDataNascimento());
		fabio.setNome(FABIO.getNome());
		fabio.setSobrenome(FABIO.getSobrenome());
		fabio.setSexo(SexoEnum.MASCULINO);
		return fabio;
	}
	
	private static ClienteDTO clienteBELTRANODTO() {
		ClienteDTO fabio=new ClienteDTO();
		fabio.setCidade(cidadeRioGrandeDTO());
		fabio.setDataNascimento(BELTRANO.getDataNascimento());
		fabio.setNome(BELTRANO.getNome());
		fabio.setSobrenome(BELTRANO.getSobrenome());
		fabio.setSexo(SexoEnum.MASCULINO);
		return fabio;
	}

	private static Optional<List<CidadeDTO>> cidadeDTOListOp() {
		List<CidadeDTO> listaCidades=Arrays.asList(CIDADEDTO);
		Optional<List<CidadeDTO>> cidade = Optional.empty();
		cidade = Optional.of(listaCidades);
		
		return cidade;
	}

	private static Optional<CidadeDTO> cidadeDTOOp() {
		Optional<CidadeDTO> cidade = Optional.empty();
		cidade = Optional.of(CIDADEDTO);
		return cidade;
	}

	private static Optional<List<Cliente>> clienteList() {
		List<Cliente> listaDeCliente= Arrays.asList(FABIO,FABIOCARVALHO);
		Optional<List<Cliente>> listClientes = Optional.empty();
		listClientes = Optional.of(listaDeCliente);
		return listClientes;
	}

	private static Optional<Cliente> optionalFulano() {
		Optional<Cliente> cliente = Optional.empty();
		cliente = Optional.of(FULANO);
		return cliente;
	}

	private static Optional<Cliente> clienteOp() {
		Optional<Cliente> cliente = Optional.empty();
		cliente = Optional.of(FABIO);
		return cliente;
	}

	private static Optional<List<Cidade>> cidadeOpList() {
		List<Cidade> listaCidades=Arrays.asList(CIDADE_PORTO_ALEGRE,CIDADE_VIAMAO);
		Optional<List<Cidade>> cidade = Optional.empty();
		cidade = Optional.of(listaCidades);
		
		return cidade;
	}

	private static Optional<Cidade> cidadeOP() {
		Optional<Cidade> cidade = Optional.empty();
		cidade = Optional.of(CIDADE_PORTO_ALEGRE);
		return cidade;
	}

	private static Cliente fulanoTal() {
		LocalDate dataNascimento = LocalDate.of(1993, 10, 21);
		Cliente cliente = new Cliente();
		cliente.setNome("FULANO");
		cliente.setSobrenome("TAL");
		cliente.setSexo(SexoEnum.MASCULINO);
		cliente.setIdade(26);
		cliente.setDataNascimento(dataNascimento);
		cliente.setCidade(CIDADE_VIAMAO);
		return cliente;
	}

	private static Cidade cidadeTres() {
		Cidade viamao = new Cidade();
		viamao.setNome("viamao");
		viamao.setEstado("rio grande do sul");
		return viamao;
	}

	private static ClienteAtualizarForm clienteExiste() {
		return new ClienteAtualizarForm(null, null, null);
	}

	private static Cliente clienteTres() {
		LocalDate dataNascimento = LocalDate.of(1993, 10, 21);
		Cliente cliente = new Cliente();
		cliente.setNome("FABIO");
		cliente.setSobrenome("KOPEZINSKI");
		cliente.setSexo(SexoEnum.MASCULINO);
		cliente.setIdade(26);
		cliente.setDataNascimento(dataNascimento);
		cliente.setCidade(RIO_GRANDE);
		return cliente;
	}

	private static Cliente beltrano() {
		LocalDate dataNascimento = LocalDate.of(1993, 10, 21);
		Cliente cliente = new Cliente();
		cliente.setNome("BELTRANO");
		cliente.setSobrenome("DE TAL");
		cliente.setSexo(SexoEnum.MASCULINO);
		cliente.setIdade(26);
		cliente.setDataNascimento(dataNascimento);
		cliente.setCidade(RIO_GRANDE);
		return cliente;
	}

	private static CidadeForm cidadeRioGrandeForm() {
		CidadeForm rioGrande = new CidadeForm("RIO GRANDE", "RIO GRANDE DO SUL");
		return rioGrande;
	}

	private static Cidade cidadeRioGrande() {
		Cidade rioGrande = new Cidade();
		rioGrande.setNome("RIO GRANDE");
		rioGrande.setEstado("RIO GRANDE DO SUL");
		return rioGrande;
	}
	
	private static CidadeDTO cidadeRioGrandeDTO() {
		CidadeDTO rioGrande = new CidadeDTO();
		rioGrande.setNome("RIO GRANDE");
		rioGrande.setEstado("RIO GRANDE DO SUL");
		return rioGrande;
	}

	private static ClienteAtualizarForm atualizarFulano() {
		ClienteAtualizarForm atualizar = new ClienteAtualizarForm(null, null, null);
		return atualizar;
	}

	private static ClienteForm clienteExistente() {
		LocalDate dataNascimento = LocalDate.of(1993, 10, 21);
		return new ClienteForm("FULANO", "TAL", SexoEnum.MASCULINO, dataNascimento, CIDADE_Form);
	}

	private static ClienteForm clienteNovo() {
		LocalDate dataNascimento = LocalDate.of(1993, 10, 21);
		return new ClienteForm("FULANO", "CARVALHO", SexoEnum.MASCULINO, dataNascimento, CIDADE_Form);
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
		cliente.setCidade(CIDADE_VIAMAO);
		return cliente;
	}

	private static ClienteForm clienteJaExiste() {
		LocalDate dataNascimento = LocalDate.of(1993, 10, 21);
		return new ClienteForm("FULANO", "TAL", SexoEnum.MASCULINO, dataNascimento, CIDADE_Form);
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
		cliente.setNome("fabio");
		cliente.setSobrenome("kopezinski");
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
		ClienteForm cliente = new ClienteForm("fulano", "tal", SexoEnum.MASCULINO, dataNascimento, CIDADE_Form);
		return cliente;
	}

	private static CidadeForm cidadeForm() {

		CidadeForm cidade = new CidadeForm("viamao", "rio grande do sul");

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
	
	private static CidadeDTO cidadeDTOViamao() {
		
		return new CidadeDTO("VIAMAO", "RIO GRANDE DO SUL");
	}



}
