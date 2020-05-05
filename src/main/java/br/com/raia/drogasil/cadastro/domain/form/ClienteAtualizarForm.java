package br.com.raia.drogasil.cadastro.domain.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteAtualizarForm {
	
	@NotNull(message = "Informar um id")
	private Integer id;
	
	@NotBlank(message = "Informar um nome")
	private String nome;
	
	@NotBlank(message = "Informar um sobrenome")
	private String sobrenome;
}
