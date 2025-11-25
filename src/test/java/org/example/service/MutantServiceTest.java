package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// aca se testea el servicio que usa el detector y el repositorio para ver que guarde bien y no duplique adn
public class MutantServiceTest {

    private MutantDetector detector;
    private DnaRecordRepository repository;
    private MutantService service;

    @BeforeEach
    public void setUp() {
        detector = Mockito.spy(new MutantDetector());
        repository = Mockito.mock(DnaRecordRepository.class);
        service = new MutantService(detector, repository);
    }

    @Test
    public void testAnalizarDnaUsaRegistroExistente() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        DnaRecord guardado = new DnaRecord();
        guardado.setDnaHash("hash");
        guardado.setMutant(true);
        guardado.setCreatedAt(LocalDateTime.now());

        when(repository.findByDnaHash(anyString())).thenReturn(Optional.of(guardado));

        boolean resultado = service.analizarDna(dna);

        assertTrue(resultado);
        verify(detector, never()).isMutant(any());
        verify(repository, never()).save(any());
    }

    @Test
    public void testAnalizarDnaNuevoMutante() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());

        boolean resultado = service.analizarDna(dna);

        assertTrue(resultado);

        ArgumentCaptor<DnaRecord> captor = ArgumentCaptor.forClass(DnaRecord.class);
        verify(repository).save(captor.capture());

        DnaRecord registro = captor.getValue();
        assertNotNull(registro.getDnaHash());
        assertEquals(64, registro.getDnaHash().length()); // hash tipo SHA-256
        assertTrue(registro.isMutant());
        assertNotNull(registro.getCreatedAt());
    }

    @Test
    public void testAnalizarDnaNuevoHumano() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };

        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());

        boolean resultado = service.analizarDna(dna);

        assertFalse(resultado);

        ArgumentCaptor<DnaRecord> captor = ArgumentCaptor.forClass(DnaRecord.class);
        verify(repository).save(captor.capture());

        DnaRecord registro = captor.getValue();
        assertFalse(registro.isMutant());
        assertNotNull(registro.getDnaHash());
        assertEquals(64, registro.getDnaHash().length()); // formato hash
        assertNotNull(registro.getCreatedAt());
    }

    @Test
    public void testAnalizarDnaGeneraMismoHashParaMismoAdn() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());

        service.analizarDna(dna);
        service.analizarDna(dna);

        ArgumentCaptor<String> hashCaptor = ArgumentCaptor.forClass(String.class);
        verify(repository, times(2)).findByDnaHash(hashCaptor.capture());

        List<String> hashes = hashCaptor.getAllValues();
        assertEquals(2, hashes.size());
        assertEquals(hashes.get(0), hashes.get(1)); // mismo hash para mismo ADN
    }

    @Test
    public void testAnalizarDnaLlamaDetectorCuandoNoHayRegistroPrevio() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());

        service.analizarDna(dna);

        verify(detector, times(1)).isMutant(dna);
    }
}
