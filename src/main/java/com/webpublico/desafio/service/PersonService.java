package com.webpublico.desafio.service;

import com.webpublico.desafio.models.PersonModel;
import com.webpublico.desafio.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public PersonModel savePerson(PersonModel personModel) {
        return personRepository.save(personModel);
    }

    public List<PersonModel>  listAll() {
        return personRepository.findAll();
    }

    public Optional<PersonModel> findByID(Long id) {
        return personRepository.findById(id);
    }

    public void excluir(PersonModel personModel){
        personRepository.delete(personModel);
    }

}
