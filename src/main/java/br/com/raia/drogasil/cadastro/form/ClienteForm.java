package br.com.raia.drogasil.cadastro.form;

import java.util.Calendar;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.raia.drogasil.cadastro.enumeration.SexoEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteForm {

	@NotBlank(message = "informe seu nome")
	private String nome;
	@NotBlank(message = "Informe seu sobrenome")
	private String sobrenome;
	@NotNull(message = "Informe seu sexo")
	private SexoEnum sexo;
	@NotNull(message = "Informe sua idade")
	private Integer idade;
	@NotNull(message = "Informe sua data de nascimento")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Calendar dataNascimento;
	@NotNull(message = "Por favor, insira um endereço")
	private CidadeForm cidade;

}
