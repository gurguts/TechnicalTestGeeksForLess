package com.example.technicaltestgeeksforless.restControllers;

import com.example.technicaltestgeeksforless.dto.EquationDTO;
import com.example.technicaltestgeeksforless.exceptions.AlreadySavedEquationException;
import com.example.technicaltestgeeksforless.exceptions.AlreadySavedRootException;
import com.example.technicaltestgeeksforless.exceptions.EquationException;
import com.example.technicaltestgeeksforless.exceptions.RootException;
import com.example.technicaltestgeeksforless.models.Root;
import com.example.technicaltestgeeksforless.services.EquationService;
import com.example.technicaltestgeeksforless.services.RootService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for root-related operations, handling HTTP requests to the '/api/v1/root' endpoint.
 */
@RestController
@RequestMapping("/api/v1/root")
public class RootRestController {
    private final EquationService equationService;
    private final RootService rootService;

    public RootRestController(EquationService equationService, RootService rootService) {
        this.equationService = equationService;
        this.rootService = rootService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveEquationAndRoot(@RequestBody EquationDTO equationDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            equationService.checkAndSaveRoot(equationDTO);

            response.put("equationMessage", "Equation correct and saved");
            response.put("rootsMessage", "The root are correct and preserved");
            return ResponseEntity.ok(response);
        } catch (AlreadySavedEquationException exception) {
            response.put("equationMessage", exception.getMessage());
            response.put("rootsMessage", "The root are correct and preserved");
            return ResponseEntity.ok(response);
        } catch (AlreadySavedRootException exception) {
            response.put("equationMessage", "Equation correct");
            response.put("rootsMessage", exception.getMessage());
            return ResponseEntity.ok(response);
        } catch (EquationException exception) {
            response.put("equationMessage", exception.getMessage());
            response.put("rootsMessage", "No way to check root due to wrong equation");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        } catch (RootException exception) {
            response.put("equationMessage", "Equation correct");
            response.put("rootsMessage", exception.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<Root>> getAllRoots() {
        return ResponseEntity.ok(rootService.findAllRoots());
    }

    @GetMapping("/find/root/{rootValue}")
    public ResponseEntity<List<Root>> findEquationsByRoot(@PathVariable double rootValue) {
        List<Root> roots = rootService.findByRoot(rootValue);
        return ResponseEntity.ok(roots);
    }

    @GetMapping("/find/number/{number}")
    public ResponseEntity<List<Root>> findEquationsByNumber(@PathVariable String number) {
        List<Root> roots = rootService.findByNumber(number);
        return ResponseEntity.ok(roots);
    }
}
