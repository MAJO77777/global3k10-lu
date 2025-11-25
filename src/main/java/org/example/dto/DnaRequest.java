package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.validation.ValidDnaSequence;

@Schema(description = "Request con la secuencia de ADN a analizar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DnaRequest {

    @NotNull(message = "El arreglo dna no puede ser nulo")
    @NotEmpty(message = "El arreglo dna no puede venir vacío")
    @ValidDnaSequence
    @Schema(
            description = "Matriz NxN representada como arreglo de strings",
            example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]"
    )
    private String[] dna;
}
