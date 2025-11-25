package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MutantController.class)
public class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MutantService mutantService;

    @MockBean
    private StatsService statsService;

    @Test
    public void testMutantEndpoint_ReturnOk() throws Exception {
        String body = """
                {
                  "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
                }
                """;

        when(mutantService.analizarDna(any())).thenReturn(true);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    public void testHumanEndpoint_ReturnForbidden() throws Exception {
        String body = """
                {
                  "dna": ["ATGCGA","CAGTGC","TTATGT","AGACGG","GCGTCA","TCACTG"]
                }
                """;

        when(mutantService.analizarDna(any())).thenReturn(false);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testInvalidDnaCharacters_ReturnBadRequest() throws Exception {
        String body = """
                {
                  "dna": ["ATGCGA","CAGTGC","TTATGT","AGACGG","GCXTCA","TCACTG"]
                }
                """;

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testNullDna_ReturnBadRequest() throws Exception {
        String body = """
                {
                  "dna": null
                }
                """;

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEmptyDnaArray_ReturnBadRequest() throws Exception {
        String body = """
                {
                  "dna": []
                }
                """;

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testMutantEndpoint_WithoutBody_ReturnBadRequest() throws Exception {
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testStatsEndpoint_ReturnOk() throws Exception {
        StatsResponse stats = new StatsResponse(40L, 100L, 0.4);
        when(statsService.obtenerStats()).thenReturn(stats);

        mockMvc.perform(get("/stats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(40))
                .andExpect(jsonPath("$.count_human_dna").value(100))
                .andExpect(jsonPath("$.ratio").value(0.4));
    }

    @Test
    public void testStatsEndpoint_NoData_ReturnZeroes() throws Exception {
        StatsResponse stats = new StatsResponse(0L, 0L, 0.0);
        when(statsService.obtenerStats()).thenReturn(stats);

        mockMvc.perform(get("/stats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(0))
                .andExpect(jsonPath("$.count_human_dna").value(0))
                .andExpect(jsonPath("$.ratio").value(0.0));
    }
}
