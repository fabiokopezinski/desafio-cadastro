package br.com.raia.drogasil.cadastro.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import br.com.raia.drogasil.cadastro.domain.model.Cliente;

@Repository
@RepositoryRestResource(collectionResourceRel = "cliente",path="cliente")
public interface ClienteRepository extends PagingAndSortingRepository<Cliente, Integer> {

	Optional<Cliente> findByNomeAndSobrenome(String nome, String sobreNome);
	Optional<Cliente> findBySobrenome(String sobreNome);

	Optional<List<Cliente>> findByNome(String nome);
}
