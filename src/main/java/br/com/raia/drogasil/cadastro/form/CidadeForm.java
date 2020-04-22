package br.com.raia.drogasil.cadastro.form;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeForm {

	@NotBlank(message = "Nome da cidade deve ser informado")
	private String nome;
	@NotBlank(message = "Nome do estado deve ser informado")
	private String estado;

}
