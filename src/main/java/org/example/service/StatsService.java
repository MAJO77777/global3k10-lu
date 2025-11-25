package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final DnaRecordRepository repository;

    public StatsResponse obtenerStats() {
        long mutantes = repository.countByMutant(true);
        long humanos = repository.countByMutant(false);

        double ratio;
        if (humanos == 0) {
            ratio = mutantes > 0 ? mutantes : 0.0;
        } else {
            ratio = (double) mutantes / humanos;
        }

        return new StatsResponse(mutantes, humanos, ratio);
    }
}
