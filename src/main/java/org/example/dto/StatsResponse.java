package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Estadísticas generales de los ADN analizados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsResponse {

    @JsonProperty("count_mutant_dna")
    @Schema(description = "Cantidad de ADN mutante almacenado")
    private long countMutantDna;

    @JsonProperty("count_human_dna")
    @Schema(description = "Cantidad de ADN humano almacenado")
    private long countHumanDna;

    @Schema(description = "Relación mutantes / humanos")
    private double ratio;
}
