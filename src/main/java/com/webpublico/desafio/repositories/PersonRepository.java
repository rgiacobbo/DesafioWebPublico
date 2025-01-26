package com.webpublico.desafio.repositories;

import com.webpublico.desafio.models.PersonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonRepository extends JpaRepository<PersonModel, Long> {
    boolean existsByNumeroDeCadastro(Long numeroDeCadastro);

}
