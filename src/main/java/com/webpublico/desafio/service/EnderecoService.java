package com.webpublico.desafio.service;

import com.webpublico.desafio.models.EnderecoModel;
import com.webpublico.desafio.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public EnderecoModel salvarEndereco(EnderecoModel enderecoModel) {
        return enderecoRepository.save(enderecoModel);
    }

    public EnderecoModel ataulizaEndereco(EnderecoModel enderecoModel) {
        return enderecoRepository.save(enderecoModel);
    }

    public List<EnderecoModel> listaTodosOsEndereco() {
        return enderecoRepository.findAll();
    }

    public Optional<EnderecoModel> BuscaPorId(Long id) {
        return enderecoRepository.findById(id);
    }

    public void excluir(EnderecoModel enderecoModel){
        enderecoRepository.delete(enderecoModel);
    }
}
