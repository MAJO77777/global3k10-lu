package org.example.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {

    private String mensaje;
    private List<String> detalles = new ArrayList<>();

    public ErrorResponse(String mensaje) {
        this.mensaje = mensaje;
    }

    public ErrorResponse(String mensaje, List<String> detalles) {
        this.mensaje = mensaje;
        if (detalles != null) {
            this.detalles.addAll(detalles);
        }
    }

    public void addDetalle(String detalle) {
        this.detalles.add(detalle);
    }
}
