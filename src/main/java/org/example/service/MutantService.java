package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.DnaRecord;
import org.example.exception.DnaHashCalculationException;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MutantService {

    private final MutantDetector detector;
    private final DnaRecordRepository repository;

    public boolean analizarDna(String[] dna) {
        String hash = calcularHash(dna);

        Optional<DnaRecord> existente = repository.findByDnaHash(hash);
        if (existente.isPresent()) {
            return existente.get().isMutant();
        }

        boolean esMutante = detector.isMutant(dna);

        DnaRecord registro = new DnaRecord();
        registro.setDnaHash(hash);
        registro.setMutant(esMutante);
        registro.setCreatedAt(LocalDateTime.now());
        repository.save(registro);

        return esMutante;
    }

    private String calcularHash(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            String concatenado = String.join("", dna);
            byte[] hashBytes = digest.digest(concatenado.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder(hashBytes.length * 2);
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new DnaHashCalculationException("No se pudo calcular el hash del ADN", e);
        }
    }
}
