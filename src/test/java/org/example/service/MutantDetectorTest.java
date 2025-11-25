package org.example.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MutantDetectorTest {

    private final MutantDetector detector = new MutantDetector();

    @Test
    public void testMutantWithHorizontalAndVertical() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        assertTrue(detector.isMutant(dna));
    }

    @Test
    public void testMutantWithDiagonalSequence() {
        String[] dna = {
                "ATGCGA",
                "CAGTAC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        assertTrue(detector.isMutant(dna));
    }

    @Test
    public void testMutantWithReverseDiagonalSequence() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGGAGG",
                "CCTCTA",
                "TCACTG"
        };

        assertTrue(detector.isMutant(dna));
    }

    @Test
    public void testHumanWithNoSequences() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAC"
        };

        assertFalse(detector.isMutant(dna));
    }

    @Test
    public void testHumanWithOnlyOneHorizontalSequence() {
        String[] dna = {
                "AAAA",
                "CAGT",
                "TTCT",
                "AGAC"
        };

        assertFalse(detector.isMutant(dna));
    }

    @Test
    public void testHumanWithOnlyOneVerticalSequence() {
        String[] dna = {
                "AAGC",
                "ATGT",
                "ATAT",
                "AGAC"
        };

        assertFalse(detector.isMutant(dna));
    }

    @Test
    public void testInvalidDnaNonSquare() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT"
        };

        assertFalse(detector.isMutant(dna));
    }

    @Test
    public void testInvalidDnaCharacters() {
        String[] dna = {
                "ATGX",
                "CAGT",
                "TTAT",
                "AGAC"
        };

        assertFalse(detector.isMutant(dna));
    }

    @Test
    public void testNullDnaArray() {
        assertFalse(detector.isMutant(null));
    }

    @Test
    public void testEmptyDnaArray() {
        String[] dna = {};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    public void testSmallMatrixWithoutSequences() {
        String[] dna = {
                "AT",
                "GA"
        };

        assertFalse(detector.isMutant(dna));
    }

    @Test
    public void testMutantWithTwoHorizontals() {
        String[] dna = {
                "AAAA",
                "CAGT",
                "TTTT",
                "AGAC"
        };

        assertTrue(detector.isMutant(dna));
    }

    @Test
    public void testMutantWithVerticalSequence() {
        String[] dna = {
                "AAGC",
                "AAGT",
                "AAGT",
                "AAGC"
        };

        assertTrue(detector.isMutant(dna));
    }

    @Test
    public void testHumanLargeMatrix() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };

        assertFalse(detector.isMutant(dna));
    }

    @Test
    public void testMutantLargeMatrix() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        assertTrue(detector.isMutant(dna));
    }

    @Test
    public void testMutantWithBorderSequences() {
        String[] dna = {
                "AAAAGG",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "GGGGTT"
        };

        assertTrue(detector.isMutant(dna));
    }

    @Test
    public void testMutantWithTwoHorizontalsInSameRow() {
        String[] dna = {
                "AAAATTTT",
                "CAGTCAGT",
                "TTGACTGA",
                "AGACGGAC",
                "GCGTCAGC",
                "TCACTGAC",
                "ACGTACGT",
                "TGCAAGTC"
        };

        assertTrue(detector.isMutant(dna));
    }

    @Test
    public void testHumanWithOnlyOneDiagonalSequence() {
        String[] dna = {
                "ATCG",
                "CAGT",
                "GCAT",
                "TGCA"
        };

        assertFalse(detector.isMutant(dna));
    }

    @Test
    public void testHumanThreeByThreeMatrix() {
        String[] dna = {
                "ATG",
                "CAG",
                "TTA"
        };

        assertFalse(detector.isMutant(dna));
    }
}
