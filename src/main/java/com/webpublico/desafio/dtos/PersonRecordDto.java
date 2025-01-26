package com.webpublico.desafio.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PersonRecordDto(@NotBlank String nome, @NotNull Long numeroDeCadastro) {

}
