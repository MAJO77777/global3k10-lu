package org.example.service;

import org.springframework.stereotype.Service;

@Service
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;

    public boolean isMutant(String[] dna) {
        if (dna == null || dna.length == 0) {
            return false;
        }

        int n = dna.length;
        char[][] matriz = new char[n][];

        for (int i = 0; i < n; i++) {
            String fila = dna[i];
            if (fila == null || fila.length() != n) {
                return false;
            }
            matriz[i] = fila.toCharArray();
        }

        int secuencias = 0;

        for (int fila = 0; fila < n; fila++) {
            for (int col = 0; col < n; col++) {

                if (col <= n - SEQUENCE_LENGTH) {
                    if (hayHorizontal(matriz, fila, col)) {
                        secuencias++;
                        if (secuencias > 1) {
                            return true;
                        }
                    }
                }

                if (fila <= n - SEQUENCE_LENGTH) {
                    if (hayVertical(matriz, fila, col)) {
                        secuencias++;
                        if (secuencias > 1) {
                            return true;
                        }
                    }
                }

                if (fila <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (hayDiagonalPrincipal(matriz, fila, col)) {
                        secuencias++;
                        if (secuencias > 1) {
                            return true;
                        }
                    }
                }

                if (fila >= SEQUENCE_LENGTH - 1 && col <= n - SEQUENCE_LENGTH) {
                    if (hayDiagonalInversa(matriz, fila, col)) {
                        secuencias++;
                        if (secuencias > 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean hayHorizontal(char[][] matriz, int fila, int col) {
        char base = matriz[fila][col];
        return base == matriz[fila][col + 1]
                && base == matriz[fila][col + 2]
                && base == matriz[fila][col + 3];
    }

    private boolean hayVertical(char[][] matriz, int fila, int col) {
        char base = matriz[fila][col];
        return base == matriz[fila + 1][col]
                && base == matriz[fila + 2][col]
                && base == matriz[fila + 3][col];
    }

    private boolean hayDiagonalPrincipal(char[][] matriz, int fila, int col) {
        char base = matriz[fila][col];
        return base == matriz[fila + 1][col + 1]
                && base == matriz[fila + 2][col + 2]
                && base == matriz[fila + 3][col + 3];
    }

    private boolean hayDiagonalInversa(char[][] matriz, int fila, int col) {
        char base = matriz[fila][col];
        return base == matriz[fila - 1][col + 1]
                && base == matriz[fila - 2][col + 2]
                && base == matriz[fila - 3][col + 3];
    }
}
