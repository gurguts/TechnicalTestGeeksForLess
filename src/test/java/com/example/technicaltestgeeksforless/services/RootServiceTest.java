package com.example.technicaltestgeeksforless.services;

import com.example.technicaltestgeeksforless.dto.EquationDTO;
import com.example.technicaltestgeeksforless.exceptions.RootException;
import com.example.technicaltestgeeksforless.models.Equation;
import com.example.technicaltestgeeksforless.models.Root;
import com.example.technicaltestgeeksforless.repositories.EquationRepository;
import com.example.technicaltestgeeksforless.repositories.RootRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RootServiceTest {

    @Mock
    private RootVerificationService rootVerificationService;

    @Mock
    private RootRepository rootRepository;

    @Mock
    private EquationRepository equationRepository;

    @InjectMocks
    private RootService rootService;

    @Test
    void parseAndCheckAndSave_Success() {
        EquationDTO dto = new EquationDTO();
        dto.setEquation("x*2+3*x+2=0");
        dto.setRoot("1.0");
        when(rootVerificationService.verify(dto)).thenReturn("1.0");
        when(equationRepository.findByEquation(anyString())).thenReturn(Optional.of(new Equation()));

        rootService.parseAndCheckAndSave(dto);

        verify(rootVerificationService).verify(dto);
        verify(rootRepository).save(any(Root.class));
    }

    @Test
    void parseAndCheckAndSave_VerificationFailure() {
        EquationDTO dto = new EquationDTO();
        dto.setEquation("x*2+3*x+2=0");
        dto.setRoot("invalid");
        when(rootVerificationService.verify(dto)).thenThrow(new RootException("Invalid root"));

        assertThrows(RootException.class, () -> rootService.parseAndCheckAndSave(dto));

        verify(rootVerificationService).verify(dto);
        verify(rootRepository, never()).save(any(Root.class));
    }

    @Test
    void findAllRoots() {
        Root root1 = new Root();
        Root root2 = new Root();
        List<Root> expectedRoots = Arrays.asList(root1, root2);

        when(rootRepository.findAll()).thenReturn(expectedRoots);

        List<Root> actualRoots = rootService.findAllRoots();

        assertEquals(expectedRoots, actualRoots);
        verify(rootRepository).findAll();
    }

    @Test
    void findByRoot() {
        double rootValue = 1.0;
        Root root1 = new Root();
        Root root2 = new Root();
        List<Root> expectedRoots = Arrays.asList(root1, root2);

        when(rootRepository.findEquationByRoot(rootValue)).thenReturn(expectedRoots);

        List<Root> actualRoots = rootService.findByRoot(rootValue);

        assertEquals(expectedRoots, actualRoots);
        verify(rootRepository).findEquationByRoot(rootValue);
    }

    @Test
    void findByNumber_Success() {
        String number = "2";
        Root root1 = new Root();
        Root root2 = new Root();
        List<Root> expectedRoots = Arrays.asList(root1, root2);

        when(rootRepository.findEquationsByNumberOfRoots(2L)).thenReturn(expectedRoots);

        List<Root> actualRoots = rootService.findByNumber(number);

        assertEquals(expectedRoots, actualRoots);
        verify(rootRepository).findEquationsByNumberOfRoots(2L);
    }

    @Test
    void findByNumber_InvalidNumber() {
        String number = "invalid";

        List<Root> actualRoots = rootService.findByNumber(number);

        assertTrue(actualRoots.isEmpty());
        verify(rootRepository, never()).findEquationsByNumberOfRoots(anyLong());
    }

}
