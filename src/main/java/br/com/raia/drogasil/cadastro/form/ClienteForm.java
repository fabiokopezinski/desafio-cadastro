package br.com.raia.drogasil.cadastro.form;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
	@NotNull(message = "Informe sua data de nascimento")
	private LocalDate dataNascimento;
	@NotNull(message = "Por favor, insira um endereço")
	private CidadeForm cidade;

}
