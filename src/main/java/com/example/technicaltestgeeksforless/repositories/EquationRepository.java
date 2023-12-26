package com.example.technicaltestgeeksforless.repositories;

import com.example.technicaltestgeeksforless.models.Equation;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repository interface for 'Equation' entities, extending Spring Data's CrudRepository for basic CRUD operations.
 */
public interface EquationRepository extends CrudRepository<Equation, Long> {
    Optional<Equation> findByEquation(String equation);
}
