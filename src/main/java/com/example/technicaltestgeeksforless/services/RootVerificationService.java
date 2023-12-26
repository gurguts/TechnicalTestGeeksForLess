package com.example.technicaltestgeeksforless.services;

import com.example.technicaltestgeeksforless.dto.EquationDTO;
import com.example.technicaltestgeeksforless.exceptions.RootException;
import org.springframework.stereotype.Service;

/**
 * Service dedicated to validating the root value provided in EquationDTO for correctness and format compliance.
 */
@Service
public class RootVerificationService {
    public String verify(EquationDTO equationDTO) {
        String root = equationDTO.getRoot();
        if (root == null) {
            throw new RootException("You didn't include a single root!");
        }

        try {
            Double.parseDouble(root);
        } catch (NumberFormatException e) {
            throw new RootException("\"" + root + "\"" + " is not a valid double.");
        }

        return root;
    }
}
