package com.example.technicaltestgeeksforless.services;

import com.example.technicaltestgeeksforless.dto.EquationDTO;
import com.example.technicaltestgeeksforless.exceptions.AlreadySavedRootException;
import com.example.technicaltestgeeksforless.models.Equation;
import com.example.technicaltestgeeksforless.models.Root;
import com.example.technicaltestgeeksforless.repositories.EquationRepository;
import com.example.technicaltestgeeksforless.repositories.RootRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Service for managing roots of equations, including verification, calculation, and persistence in the database.
 */
@Service
public class RootService {
    private final RootVerificationService rootVerificationService;
    private final RootRepository rootRepository;
    private final CalculationService calculationService;
    private final EquationRepository equationRepository;

    public RootService(RootVerificationService rootVerificationService, RootRepository rootRepository,
                       EquationRepository equationRepository, CalculationService calculationService) {
        this.rootVerificationService = rootVerificationService;
        this.rootRepository = rootRepository;
        this.calculationService = calculationService;
        this.equationRepository = equationRepository;
    }

    public void parseAndCheckAndSave(EquationDTO equationDTO) {
        Double root = Double.valueOf(rootVerificationService.verify(equationDTO));
        checkAndSave(root, equationDTO.getEquation().replaceAll("\\s", ""));
    }

    private void checkAndSave(Double rootInput, String equationInput) {
        calculationService.calculate(rootInput, equationInput);
        Equation equation = equationRepository.findByEquation(equationInput)
                .orElseThrow(() -> new EntityNotFoundException("Equation not found"));
        boolean exists = rootRepository.findByRootAndEquation(rootInput, equation).isPresent();
        if (exists) {
            throw new AlreadySavedRootException("The root \"" + rootInput + "\" is correct and was previously saved");
        }
        saveRoot(rootInput, equation);
    }

    private void saveRoot(Double rootInput, Equation equation) {
        Root root = new Root();
        root.setRoot(rootInput);
        root.setEquation(equation);
        rootRepository.save(root);
        rootRepository.findAll();
    }

    public List<Root> findAllRoots() {
        return (List<Root>) rootRepository.findAll();
    }

    public List<Root> findByRoot(double rootValue) {
        return rootRepository.findEquationByRoot(rootValue);
    }

    public List<Root> findByNumber(String number) {
        long numberOfRoots;
        try {
            numberOfRoots = Long.parseLong(number);
        } catch (NumberFormatException e) {
            return Collections.emptyList();
        }
        return rootRepository.findEquationsByNumberOfRoots(numberOfRoots);
    }
}
