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

    public static String validador(long n) {
        String str = String.valueOf(n);
        if (str.length() == 11) {
            return validaCpf(str)?"Pessoa Fisica": "invalido";
        } else if (str.length() == 14) {
            return validaCnpj(str)?"Pessoa Juridica": "invalido";
        }
        return "invalido";
    }

    public static String validadorSimples(long n) {
        String str = String.valueOf(n);
        if (str.length() == 11) {
            return "Pessoa Fisica";
        } else if (str.length() == 14) {
            return "Pessoa Juridica";
        }
        return "invalido";
    }

    private static boolean validaCpf(String cpf) {
        if (cpf.matches("(\\d)\\1{10}")) return false;
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * (10 - i);
        }
        int n1 = 11 - (soma % 11);
        n1 = (n1 > 9) ? 0 : n1;
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * (11 - i);
        }
        int n2 = 11 - (soma % 11);
        n2 = (n2 > 9) ? 0 : n2;
        return n1 == (cpf.charAt(9) - '0') && n2 == (cpf.charAt(10) - '0');
    }

    private static boolean validaCnpj(String cnpj) {
        if (cnpj.matches("(\\d)\\1{13}")) return false;

        int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int soma = 0;
        for (int i = 0; i < 12; i++) {
            soma += (cnpj.charAt(i) - '0') * pesos1[i];
        }
        int n1 = 11 - (soma % 11);
        n1 = (n1 > 9) ? 0 : n1;
        soma = 0;
        for (int i = 0; i < 13; i++) {
            soma += (cnpj.charAt(i) - '0') * pesos2[i];
        }
        int n2 = 11 - (soma % 11);
        n2 = (n2 > 9) ? 0 : n2;
        return n1 == (cnpj.charAt(12) - '0') && n2 == (cnpj.charAt(13) - '0');
    }

    public PersonModel salvarValidacaoSimples(PersonModel personModel) {
        String res = validadorSimples(personModel.getNumeroDeCadastro());
        if(res.equals("invalido")){
            throw new IllegalArgumentException("Erro, não é valido.");
        }
        personModel.setIdentificador(res);
        if (personRepository.existsByNumeroDeCadastro(personModel.getNumeroDeCadastro())) {
            throw new IllegalArgumentException("Erro, o numero de cadastro já existente.");
        }
        return personRepository.save(personModel);
    }

    public PersonModel atualizaPessoa(PersonModel personModel) {
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
