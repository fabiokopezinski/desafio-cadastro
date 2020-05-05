package br.com.raia.drogasil.cadastro.domain.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = { "cliente" })
@Table(name = "CIDADE")
@SequenceGenerator(name = "CIDADE", sequenceName = "cidade_seq_id", allocationSize = 1)
public class Cidade {

	@Id
	@Column(name = "id", nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cidade_seq_id")
	
	private Integer id;
	@NotBlank
	@Column(unique = true)
	private String nome;
	@NotBlank
	private String estado;

	@OneToMany(mappedBy = "cidade")
	private List<Cliente> cliente;
}
