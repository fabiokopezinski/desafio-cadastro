package br.com.raia.drogasil.cadastro.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.raia.drogasil.cadastro.domain.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	Optional<Cliente> findByNomeAndSobrenome(String nome, String sobreNome);
	Optional<Cliente> findBySobrenome(String sobreNome);

	Optional<List<Cliente>> findByNome(String nome);
}
