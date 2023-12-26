package com.example.technicaltestgeeksforless.restControllers;

import com.example.technicaltestgeeksforless.dto.EquationDTO;
import com.example.technicaltestgeeksforless.exceptions.EquationException;
import com.example.technicaltestgeeksforless.services.EquationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EquationRestControllerTest {
    @Mock
    private EquationService equationService;

    @InjectMocks
    private EquationRestController controller;

    @Test
    void saveEquation_Success() {
        EquationDTO dto = new EquationDTO();
        dto.setEquation("someEquation");

        ResponseEntity<?> response = controller.saveEquation(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(equationService).checkAndSaveEquation(anyString());
    }

    @Test
    void saveEquation_AlreadySavedException() {
        EquationDTO dto = new EquationDTO();
        dto.setEquation("someEquation");

        ResponseEntity<?> response = controller.saveEquation(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Equation correct and saved", response.getBody());
    }

    @Test
    void saveEquation_EquationException() {
        EquationDTO dto = new EquationDTO();
        dto.setEquation("invalidEquation");

        doThrow(new EquationException("Invalid equation")).when(equationService).checkAndSaveEquation(anyString());

        ResponseEntity<?> response = controller.saveEquation(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid equation", response.getBody());
    }
}
