package br.com.raia.drogasil.cadastro.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.raia.drogasil.cadastro.enumeration.SexoEnum;
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
	@NotNull(message = "Informe sua data de nascimento")
	@JsonFormat(pattern = "dd/MM/YYYY")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	@ManyToOne
	@JoinColumn(name = "cidade_id")
	@NotNull(message = "Por favor, inserir um endereço")
	private Cidade cidade;

}
