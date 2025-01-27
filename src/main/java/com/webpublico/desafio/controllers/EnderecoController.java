package com.webpublico.desafio.controllers;

import com.webpublico.desafio.dtos.EnderecoRedordDto;
import com.webpublico.desafio.dtos.PessoaRecordDto;
import com.webpublico.desafio.models.EnderecoModel;

import com.webpublico.desafio.models.PessoaModel;
import com.webpublico.desafio.service.EnderecoService;
import com.webpublico.desafio.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EnderecoController {

    @Autowired
    EnderecoService enderecoService;

    @Autowired
    PessoaService pessoaService;

    @Operation(description = "Cadastro de Endereço. Necessario passar o id da Pessoa já cadastrada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="201", description = "Sucesso no cadastro do endereço"),
            @ApiResponse(responseCode = "404", description = "Pessoa não envontrada.")
    })
    @PostMapping("endereco/{id}")
    public ResponseEntity<Object> salvaEndereco(@PathVariable(value = "id") Long id,
                                                @RequestBody @Valid EnderecoRedordDto enderecoRedordDto) {
        Optional<PessoaModel> pessoaOptional = pessoaService.BuscaPorId(id);

        if (pessoaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não envontrada.");
        }

        PessoaModel pessoaModel = pessoaOptional.get();
        EnderecoModel enderecoModel = new EnderecoModel();
        enderecoModel.setEndereco(enderecoRedordDto.endereco());
        enderecoModel.setPessoa(pessoaModel);
        pessoaModel.getEnderecos().add(enderecoModel);
        pessoaService.salvar(pessoaModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaModel.getEnderecos());
    }

    @Operation(description = "Lista todos os endereços cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Lista de endereços")
    })
    @GetMapping("/endereco")
    public ResponseEntity<List<EnderecoModel>> listaTodosOsEndereco(){
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.listaTodosOsEndereco());
    }

    @Operation(description = "Busca um endereço específico, deve enviar o id desejado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Exibe os dados do endereço desejado.")
    })
    @GetMapping("/endereco/{id}")
    public ResponseEntity<Object> buscaEnderecoId(@PathVariable(value="id") Long id){
        Optional<EnderecoModel> endereco = enderecoService.BuscaPorId(id);
        if(endereco.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endenreço não encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(endereco.get());
    }

    @Operation(description = "Permite editar o endereço específico, deve enviar o id desejado")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Exibe os dados já atualizados."),
            @ApiResponse(responseCode = "404", description = "Endenreço não encontrado.")
    })
    @PutMapping("/endereco/{id}")
    public ResponseEntity<Object> atualizaEnderecoPorId(@PathVariable(value="id") Long id,
                                                    @RequestBody @Valid PessoaRecordDto pessoaRecordDto){
        Optional<EnderecoModel> endereco = enderecoService.BuscaPorId(id);
        if(endereco.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endenreço não encontrado.");
        }
        var enderecoModel = endereco.get();
        BeanUtils.copyProperties(pessoaRecordDto, enderecoModel);
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.ataulizaEndereco(enderecoModel));
    }

    @Operation(description = "Exclui um endereço específico, deve enviar o id desejado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Endereço excluido com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Endenreço não encontrado.")
    })
    @DeleteMapping("/endereco/{id}")
    public ResponseEntity<Object> removeEndereco(@PathVariable(value="id") Long id) {
        Optional<EnderecoModel> endereco = enderecoService.BuscaPorId(id);
        if(endereco.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endenreço não encontrado.");
        }
        enderecoService.excluir(endereco.get());
        return ResponseEntity.status(HttpStatus.OK).body("Endereço excluido com sucesso!");
    }
}
