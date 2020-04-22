package br.com.raia.drogasil.cadastro.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.raia.drogasil.cadastro.enumeration.SexoEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDto {

	private Integer id;
	private String nome;
	private String sobrenome;
	private SexoEnum sexo;
	private Integer idade;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataNascimento;
	private CidadeDto cidade;

}
