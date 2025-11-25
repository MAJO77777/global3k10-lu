package org.example.service;

import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// aca tests me fijo que el servicio de stats calcule bien los conteos y el ratio a partir de la base
public class StatsServiceTest {

    private DnaRecordRepository repository;
    private StatsService statsService;

    @BeforeEach
    public void setUp() {
        repository = mock(DnaRecordRepository.class);
        statsService = new StatsService(repository);
    }

    @Test
    public void testObtenerStatsConHumanos() {
        when(repository.countByMutant(true)).thenReturn(40L);
        when(repository.countByMutant(false)).thenReturn(100L);

        StatsResponse stats = statsService.obtenerStats();

        assertEquals(40L, stats.getCountMutantDna());
        assertEquals(100L, stats.getCountHumanDna());
        assertEquals(0.4, stats.getRatio(), 0.0001);
    }

    @Test
    public void testObtenerStatsSinHumanosConMutantes() {
        when(repository.countByMutant(true)).thenReturn(40L);
        when(repository.countByMutant(false)).thenReturn(0L);

        StatsResponse stats = statsService.obtenerStats();

        assertEquals(40L, stats.getCountMutantDna());
        assertEquals(0L, stats.getCountHumanDna());
        assertEquals(40.0, stats.getRatio(), 0.0001);
    }

    @Test
    public void testObtenerStatsSinDatos() {
        when(repository.countByMutant(true)).thenReturn(0L);
        when(repository.countByMutant(false)).thenReturn(0L);

        StatsResponse stats = statsService.obtenerStats();

        assertEquals(0L, stats.getCountMutantDna());
        assertEquals(0L, stats.getCountHumanDna());
        assertEquals(0.0, stats.getRatio(), 0.0001);
    }

    @Test
    public void testObtenerStatsConRatioDecimal() {
        when(repository.countByMutant(true)).thenReturn(1L);
        when(repository.countByMutant(false)).thenReturn(3L);

        StatsResponse stats = statsService.obtenerStats();

        assertEquals(1L, stats.getCountMutantDna());
        assertEquals(3L, stats.getCountHumanDna());
        assertEquals(0.333, stats.getRatio(), 0.001);
    }

    @Test
    public void testObtenerStatsConMismosValores() {
        when(repository.countByMutant(true)).thenReturn(50L);
        when(repository.countByMutant(false)).thenReturn(50L);

        StatsResponse stats = statsService.obtenerStats();

        assertEquals(50L, stats.getCountMutantDna());
        assertEquals(50L, stats.getCountHumanDna());
        assertEquals(1.0, stats.getRatio(), 0.0001);
    }

    @Test
    public void testObtenerStatsConNumerosGrandes() {
        when(repository.countByMutant(true)).thenReturn(1_000_000L);
        when(repository.countByMutant(false)).thenReturn(2_000_000L);

        StatsResponse stats = statsService.obtenerStats();

        assertEquals(1_000_000L, stats.getCountMutantDna());
        assertEquals(2_000_000L, stats.getCountHumanDna());
        assertEquals(0.5, stats.getRatio(), 0.0001);
    }
}
