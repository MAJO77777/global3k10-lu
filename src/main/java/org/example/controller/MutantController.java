package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.dto.DnaRequest;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Mutantes", description = "Endpoints para detección de mutantes y estadísticas")
@RequiredArgsConstructor
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    @PostMapping("/mutant")
    @Operation(summary = "Analiza un ADN y responde si corresponde a un mutante")
    public ResponseEntity<Void> esMutante(@Validated @RequestBody DnaRequest request) {
        boolean mutante = mutantService.analizarDna(request.getDna());
        if (mutante) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/stats")
    @Operation(summary = "Devuelve estadísticas de los ADN analizados")
    public ResponseEntity<StatsResponse> stats() {
        StatsResponse respuesta = statsService.obtenerStats();
        return ResponseEntity.ok(respuesta);
    }
}

