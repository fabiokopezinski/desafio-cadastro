package br.com.raia.drogasil.cadastro.domain.form;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.raia.drogasil.cadastro.domain.enumeration.SexoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
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
	@Valid
	private CidadeForm cidade;

}
