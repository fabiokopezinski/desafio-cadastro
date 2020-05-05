package br.com.raia.drogasil.cadastro.domain.dto;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.raia.drogasil.cadastro.domain.enumeration.SexoEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO {

	private Integer id;
	@NotBlank(message = "informe seu nome")
	@Valid
	private String nome;
	@NotBlank(message = "informe seu sobrenome")
	@Valid
	private String sobrenome;
	@NotBlank(message = "informe seu sexo")
	@Valid
	private SexoEnum sexo;
	@NotBlank(message = "informe sua idade")
	@Valid
	private Integer idade;
	@NotBlank(message = "informe sua data nascimento")
	@Valid
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;
	@NotBlank(message = "informe sua cidade")
	@Valid
	private CidadeDTO cidade;

}
