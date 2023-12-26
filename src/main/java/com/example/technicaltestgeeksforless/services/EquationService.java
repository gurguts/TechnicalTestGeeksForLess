package com.example.technicaltestgeeksforless.services;

import com.example.technicaltestgeeksforless.dto.EquationDTO;
import com.example.technicaltestgeeksforless.exceptions.AlreadySavedEquationException;
import com.example.technicaltestgeeksforless.models.Equation;
import com.example.technicaltestgeeksforless.repositories.EquationRepository;
import org.springframework.stereotype.Service;

/**
 * Service for verifying, saving, and managing equations and their roots,
 * integrating various sub-services for specific tasks.
 */
@Service
public class EquationService {
    private final EquationVerificationService equationVerificationService;
    private final EquationRepository equationRepository;
    private final RootService rootService;

    public EquationService(EquationVerificationService equationVerificationService,
                           EquationRepository equationRepository, RootService rootService) {
        this.equationVerificationService = equationVerificationService;
        this.equationRepository = equationRepository;
        this.rootService = rootService;
    }

    public void checkAndSaveEquation(String equationInput) {
        equationInput = equationInput.replaceAll("\\s", "");
        equationVerificationService.verify(equationInput);

        saveEquationIfNotExists(equationInput);
    }

    public void saveEquationIfNotExists(String equationInput) {
        boolean exists = equationRepository.findByEquation(equationInput).isPresent();
        if (exists) {
            throw new AlreadySavedEquationException("The equation is correct and was previously saved");
        }

        Equation equation = new Equation();
        equation.setEquation(equationInput);

        equationRepository.save(equation);
    }

    public void checkAndSaveRoot(EquationDTO equationDTO) {
        try {
            checkAndSaveEquation(equationDTO.getEquation());
            processRoot(equationDTO, null);
        } catch (AlreadySavedEquationException exception) {
            processRoot(equationDTO, exception);
        }
    }

    private void processRoot(EquationDTO equationDTO, AlreadySavedEquationException exception) {
        rootService.parseAndCheckAndSave(equationDTO);
        if (exception != null) {
            throw new AlreadySavedEquationException(exception.getMessage());
        }
    }
}
