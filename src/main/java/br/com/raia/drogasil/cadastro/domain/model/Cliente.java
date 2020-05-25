package br.com.raia.drogasil.cadastro.domain.model;

import java.time.LocalDate;
import java.time.Period;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.raia.drogasil.cadastro.domain.enumeration.SexoEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "CLIENTE")
@SequenceGenerator(name = "CLIENTE", sequenceName = "cliente_seq_id", initialValue = 1, allocationSize = 1)
public class Cliente {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq_id")
	private Integer id;
	private String nome;
	private String sobrenome;
	@Enumerated(EnumType.STRING)
	private SexoEnum sexo;
	private Integer idade;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;
	@ManyToOne
	@JoinColumn(name = "cidade_id")
	@NotNull(message = "Por favor, inserir um endereço")
	private Cidade cidade;
	
	@PrePersist
	public void setarNascimento() {
		LocalDate hoje = LocalDate.now();
		Period idadePeriod=dataNascimento.until(hoje);
		idade=idadePeriod.getYears();
		this.setNome(nome.toUpperCase());
		this.setSobrenome(sobrenome.toUpperCase());
	}
	

}
