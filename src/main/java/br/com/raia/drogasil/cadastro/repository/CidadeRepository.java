package br.com.raia.drogasil.cadastro.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.raia.drogasil.cadastro.model.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

	Optional<Cidade> findByNome(String nome);

	List<Cidade> findByEstado(String estado);

}
