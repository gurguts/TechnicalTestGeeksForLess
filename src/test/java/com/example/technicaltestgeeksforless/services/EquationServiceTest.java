package com.example.technicaltestgeeksforless.services;

import com.example.technicaltestgeeksforless.dto.EquationDTO;
import com.example.technicaltestgeeksforless.exceptions.AlreadySavedEquationException;
import com.example.technicaltestgeeksforless.models.Equation;
import com.example.technicaltestgeeksforless.repositories.EquationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EquationServiceTest {
    @Mock
    private EquationVerificationService equationVerificationService;

    @Mock
    private EquationRepository equationRepository;

    @InjectMocks
    private EquationService equationService;

    @Mock
    private RootService rootService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        equationService = new EquationService(equationVerificationService, equationRepository, rootService);
    }
    @Test
    void checkAndSaveEquation_Success() {
        String equation = "x*2 + 3*x + 2 = 0";
        String processedEquation = equation.replaceAll("\\s", "");
        when(equationRepository.findByEquation(processedEquation)).thenReturn(Optional.empty());

        equationService.checkAndSaveEquation(equation);

        verify(equationVerificationService).verify(processedEquation);
        verify(equationRepository).save(any(Equation.class));
    }

    @Test
    void saveEquationIfNotExists_AlreadyExists() {
        String equationInput = "x*2+3*x+2=0";
        when(equationRepository.findByEquation(equationInput)).thenReturn(Optional.of(new Equation()));

        assertThrows(AlreadySavedEquationException.class, () -> equationService.saveEquationIfNotExists(equationInput));

        verify(equationRepository).findByEquation(equationInput);
        verify(equationRepository, never()).save(any(Equation.class));
    }

    @Test
    void saveEquationIfNotExists_NewEquation() {
        String equationInput = "x*2+3*x+2=0";
        when(equationRepository.findByEquation(equationInput)).thenReturn(Optional.empty());

        equationService.saveEquationIfNotExists(equationInput);

        verify(equationRepository).findByEquation(equationInput);
        verify(equationRepository).save(any(Equation.class));
    }

    @Test
    void checkAndSaveRoot_Success() {
        EquationDTO dto = new EquationDTO();
        dto.setEquation("x*2+3*x+2=0");
        when(equationRepository.findByEquation(anyString())).thenReturn(Optional.empty());

        equationService.checkAndSaveRoot(dto);

        verify(equationRepository).findByEquation(dto.getEquation());
    }

    @Test
    void checkAndSaveRoot_AlreadySaved() {
        EquationDTO dto = new EquationDTO();
        dto.setEquation("x^2+3x+2=0");
        when(equationRepository.findByEquation(anyString())).thenReturn(Optional.of(new Equation()));

        assertThrows(AlreadySavedEquationException.class, () -> equationService.checkAndSaveRoot(dto));

        verify(equationRepository).findByEquation(dto.getEquation());
    }

    @Test
    void processRoot_ThrowsAlreadySavedException() {
        EquationDTO dto = new EquationDTO();
        dto.setEquation("x*2+3*x+2=0");
        when(equationRepository.findByEquation(dto.getEquation())).thenReturn(Optional.of(new Equation()));

        assertThrows(AlreadySavedEquationException.class, () -> equationService.checkAndSaveRoot(dto));

        verify(rootService).parseAndCheckAndSave(dto);
    }

    @Test
    void processRoot_Success() {
        EquationDTO dto = new EquationDTO();
        dto.setEquation("x*2+3*x+2=0");
        when(equationRepository.findByEquation(dto.getEquation())).thenReturn(Optional.empty());

        equationService.checkAndSaveRoot(dto);

        verify(rootService).parseAndCheckAndSave(dto);
    }


}
