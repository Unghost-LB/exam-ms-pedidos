package com.examen.pedidos.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String mensaje;
    private String detalle;
    private LocalDateTime fecha;
}
