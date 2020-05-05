package br.com.raia.drogasil.cadastro.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.raia.drogasil.cadastro.domain.model.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

	Optional<Cidade> findByNome(String nome);
	
	Optional<List<Cidade>> findByEstado(String estado);

}
