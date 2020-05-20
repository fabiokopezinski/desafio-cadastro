package br.com.raia.drogasil.cadastro.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import br.com.raia.drogasil.cadastro.domain.model.Cidade;

@Repository
@RepositoryRestResource(collectionResourceRel = "cidade",path="cidade")
public interface CidadeRepository extends PagingAndSortingRepository<Cidade, Integer> {

	Optional<Cidade> findByNome(String nome);
	
	Optional<List<Cidade>> findByEstado(String estado);

}
