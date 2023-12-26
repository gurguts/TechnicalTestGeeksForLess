package com.example.technicaltestgeeksforless.models;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity class representing a root, mapped to the 'roots' table in the database.
 */
@Data
@Entity
@Table(name = "roots")
public class Root {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "EquationID", nullable = false)
    private Equation equation;

    @Column(name = "root")
    private double root;
}
