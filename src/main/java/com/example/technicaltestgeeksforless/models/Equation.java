package com.example.technicaltestgeeksforless.models;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity class representing an equation, mapped to the 'equations' table in the database.
 */
@Data
@Entity
@Table(name = "equations")
public class Equation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Equation")
    private String equation;
}
