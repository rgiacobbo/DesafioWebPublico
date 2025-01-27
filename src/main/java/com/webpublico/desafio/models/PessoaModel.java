package com.webpublico.desafio.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Schema(description = "Schema de Pessoa.",title = "Pessoa")
@Entity
@Table(name = "tbperson")
public class PessoaModel implements Serializable {
    private static final long serialVersionUIDLONG = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String nome;
    @Schema(description = "Dependendo do numero passando em numeroDeCadastro é definido em Pessoa Fisica ou Juridica.")
    private String identificador;
    @Schema(description = "Aceita tanto CPF ou CNPJ.")
    private Long numeroDeCadastro;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnderecoModel> enderecos;

    public List<EnderecoModel> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoModel> enderecos) {
        this.enderecos = enderecos;
    }

    public Long getNumeroDeCadastro() {
        return numeroDeCadastro;
    }

    public void setNumeroDeCadastro(Long numeroDeCadastro) {
        this.numeroDeCadastro = numeroDeCadastro;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
