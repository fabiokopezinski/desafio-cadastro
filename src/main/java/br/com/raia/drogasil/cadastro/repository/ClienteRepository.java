package br.com.raia.drogasil.cadastro.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.raia.drogasil.cadastro.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	Optional<Cliente> findByNomeAndSobrenome(String nome,String sobreNome);
	
	List<Cliente> findByNome(String nome);
}
