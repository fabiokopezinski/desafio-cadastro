package br.com.raia.drogasil.cadastro.domain.form;

import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClienteAtualizarForm {
	
	@NotNull(message = "Informar um id")
	private Integer id;
	
	@NotBlank(message = "Informar um nome")
	private String nome;
	
	@NotBlank(message = "Informar um sobrenome")
	private String sobrenome;
	
	@PrePersist
	public void setar() {
		this.setNome(nome.toUpperCase());
		this.setSobrenome(sobrenome.toUpperCase());
	}
}
