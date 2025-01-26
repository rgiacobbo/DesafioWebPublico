package com.webpublico.desafio.controllers;


import com.webpublico.desafio.dtos.PersonRecordDto;
import com.webpublico.desafio.models.PersonModel;
import com.webpublico.desafio.repositories.PersonRepository;
import com.webpublico.desafio.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {

    @Autowired
    PersonService personService;

    @PostMapping("/persons")
    public ResponseEntity<PersonModel> salvarPessoa(@RequestBody @Valid PersonRecordDto personRecordDto) {
        var personModel = new PersonModel();
        BeanUtils.copyProperties(personRecordDto, personModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.savePerson(personModel));
    }

    @GetMapping("persons")
    public ResponseEntity<List<PersonModel>> listarTodasAsPessoas(){
        return ResponseEntity.status(HttpStatus.OK).body(personService.listAll());
    }

    @GetMapping("persons/{id}")
    public ResponseEntity<Object> procurarPessoaId(@PathVariable(value="id") Long id){
        Optional<PersonModel> pessoa = personService.findByID(id);
        if(pessoa.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(pessoa.get());
    }

    @PutMapping("/persons/{id}")
    public ResponseEntity<Object> atualizarPessoaId(@PathVariable(value="id") Long id,
                                                    @RequestBody @Valid PersonRecordDto personRecordDto){
        Optional<PersonModel> pessoa = personService.findByID(id);
        if(pessoa.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found.");
        }
        var personModel = pessoa.get();
        BeanUtils.copyProperties(personRecordDto, personModel);
        return ResponseEntity.status(HttpStatus.OK).body(personService.savePerson(personModel));
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<Object> deletarPessoa(@PathVariable(value="id") Long id) {
        Optional<PersonModel> pessoa = personService.findByID(id);
        if(pessoa.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found.");
        }
        personService.excluir(pessoa.get());
        return ResponseEntity.status(HttpStatus.OK).body("Pessoa excluida com sucesso!");
    }


}
