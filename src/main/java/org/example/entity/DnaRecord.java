package org.example.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dna_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DnaRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dna_hash", nullable = false, unique = true, length = 128)
    private String dnaHash;

    @Column(name = "is_mutant", nullable = false)
    private boolean mutant;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
