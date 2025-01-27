package com.webpublico.desafio.dtos;

import jakarta.validation.constraints.NotBlank;

public record EnderecoRedordDto(@NotBlank String endereco) {
}
