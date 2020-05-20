package br.com.raia.drogasil.cadastro.domain.form;

import javax.validation.constraints.NotBlank;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class CidadeForm {

	@NotBlank(message = "Nome da cidade deve ser informado")
	private String nome;
	@NotBlank(message = "Nome do estado deve ser informado")
	private String estado;

}
