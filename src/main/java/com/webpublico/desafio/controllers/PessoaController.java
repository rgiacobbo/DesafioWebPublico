package com.webpublico.desafio.controllers;

import com.webpublico.desafio.dtos.PessoaRecordDto;
import com.webpublico.desafio.models.PessoaModel;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
public class PessoaController {

    @Autowired
    PessoaService pessoaService;

    @Operation(description = "Uploading para popular o banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Sucesso no Uploading."),
            @ApiResponse(responseCode = "400", description = "O arquivo está vazio."),
            @ApiResponse(responseCode = "500", description = "Erro: Mensagem do Erro.")
    })
    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O arquivo está vazio.");
        }
        try {
            List<PessoaModel> pessoas = pessoaService.cadastrarPessoas(file);
            return ResponseEntity.status(HttpStatus.OK).body("Sucesso no Uploading.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }
    }

    @Operation(description = "Cadastro de Pessoas. Depedendo o numero de cadastro vai defenir se é uma Pessoa Fisica ou Pessoa Juridica")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="201", description = "Sucesso no cadastro"),
            @ApiResponse(responseCode = "500", description = "Erro:")
    })
    @PostMapping("/persons")
    public ResponseEntity<PessoaModel> salvarPessoa(@RequestBody @Valid PessoaRecordDto pessoaRecordDto) {
        var pessoaModel = new PessoaModel();
        BeanUtils.copyProperties(pessoaRecordDto, pessoaModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.salvarValidacaoSimples(pessoaModel));
    }

    @Operation(description = "Listagem de Pessoas cadastradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Exibe uma lista de todas as pessoas cadastradas.")
    })
    @GetMapping("/persons")
    public ResponseEntity<List<PessoaModel>> listarTodasAsPessoas(){
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.listaTodasAsPessoas());
    }

    @Operation(description = "Exibe os dados de uma Pessoa em específico, deve enviar o id desejado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Exibe os dados."),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada.")
    })
    @GetMapping("/persons/{id}")
    public ResponseEntity<Object> procurarPessoaId(@PathVariable(value="id") Long id){
        Optional<PessoaModel> pessoa = pessoaService.BuscaPorId(id);
        if(pessoa.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(pessoa.get());
    }

    @Operation(description = "Atualiza dados de uma Pessoa")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Exibe os dados atualizados."),
            @ApiResponse(responseCode ="404", description = "Pessoa não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro:")
    })
    @PutMapping("/persons/{id}")
    public ResponseEntity<Object> atualizarPessoaId(@PathVariable(value="id") Long id,
                                                    @RequestBody @Valid PessoaRecordDto pessoaRecordDto){
        try {
        Optional<PessoaModel> pessoa = pessoaService.BuscaPorId(id);
        if(pessoa.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada.");
        }
        var pessoaModel = pessoa.get();
        BeanUtils.copyProperties(pessoaRecordDto, pessoaModel);
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.atualizaPessoa(pessoaModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }
    }

    @Operation(description = "Exclui o cadastro de uma Pessoa em específico, deve enviar o id desejado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Pessoa excluida com sucesso!"),
            @ApiResponse(responseCode ="404", description = "Pessoa não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro:")
    })
    @DeleteMapping("/persons/{id}")
    public ResponseEntity<Object> deletarPessoa(@PathVariable(value="id") Long id) {
        try {
        Optional<PessoaModel> pessoa = pessoaService.BuscaPorId(id);
        if(pessoa.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada.");
        }
        pessoaService.excluir(pessoa.get());
        return ResponseEntity.status(HttpStatus.OK).body("Pessoa excluida com sucesso!");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
    }
    }


}
