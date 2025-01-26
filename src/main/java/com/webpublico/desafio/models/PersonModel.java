package com.webpublico.desafio.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tbperson")
public class PersonModel implements Serializable {
    private static final long serialVersionUIDLONG = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private String name;
    private BigDecimal cpf;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCpf() {
        return cpf;
    }

    public void setCpf(BigDecimal cpf) {
        this.cpf = cpf;
    }
}
