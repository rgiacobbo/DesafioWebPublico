package com.webpublico.desafio.repositories;

import com.webpublico.desafio.models.PessoaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PessoaRepository extends JpaRepository<PessoaModel, Long> {
    boolean existsByNumeroDeCadastro(Long numeroDeCadastro);

}
