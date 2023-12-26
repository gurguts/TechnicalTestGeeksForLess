package com.example.technicaltestgeeksforless.repositories;

import com.example.technicaltestgeeksforless.models.Equation;
import com.example.technicaltestgeeksforless.models.Root;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for 'Root' entities, extending Spring Data's CrudRepository for basic CRUD operations.
 */
public interface RootRepository extends CrudRepository<Root, Long> {
    Optional<Root> findByRootAndEquation(double root, Equation equation);

    List<Root> findEquationByRoot(double rootValue);

    @Query("SELECT r FROM Root r WHERE (SELECT COUNT(r2) FROM Root r2 WHERE r2.equation = r.equation) = :numberOfRoots")
    List<Root> findEquationsByNumberOfRoots(@Param("numberOfRoots") long numberOfRoots);

}
