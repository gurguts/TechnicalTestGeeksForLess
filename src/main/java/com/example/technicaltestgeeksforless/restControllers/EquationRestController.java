package com.example.technicaltestgeeksforless.restControllers;

import com.example.technicaltestgeeksforless.dto.EquationDTO;
import com.example.technicaltestgeeksforless.exceptions.AlreadySavedEquationException;
import com.example.technicaltestgeeksforless.exceptions.EquationException;
import com.example.technicaltestgeeksforless.services.EquationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for equation-related operations, handling HTTP requests to the '/api/v1/equation' endpoint.
 */
@RestController
@RequestMapping("/api/v1/equation")
public class EquationRestController {

    private final EquationService equationService;

    public EquationRestController(EquationService equationService) {
        this.equationService = equationService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveEquation(@RequestBody EquationDTO equationDTO) {
        try {
            equationService.checkAndSaveEquation(equationDTO.getEquation());
            return ResponseEntity.ok("Equation correct and saved");
        } catch (AlreadySavedEquationException exception) {
            return ResponseEntity.ok(exception.getMessage());
        } catch (EquationException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(exception.getMessage());
        }
    }
}
