package com.examen.pedidos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EstadoRequestDTO {

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;
}
