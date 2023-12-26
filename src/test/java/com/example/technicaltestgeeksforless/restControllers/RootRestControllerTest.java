package com.example.technicaltestgeeksforless.restControllers;

import com.example.technicaltestgeeksforless.dto.EquationDTO;
import com.example.technicaltestgeeksforless.exceptions.AlreadySavedEquationException;
import com.example.technicaltestgeeksforless.exceptions.AlreadySavedRootException;
import com.example.technicaltestgeeksforless.exceptions.EquationException;
import com.example.technicaltestgeeksforless.exceptions.RootException;
import com.example.technicaltestgeeksforless.models.Root;
import com.example.technicaltestgeeksforless.services.EquationService;
import com.example.technicaltestgeeksforless.services.RootService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class RootRestControllerTest {

    @Mock
    private EquationService equationService;

    @Mock
    private RootService rootService;

    @InjectMocks
    private RootRestController controller;

    @Test
    void getAllRoots_ReturnsRootList() {
        List<Root> mockRoots = List.of(new Root(), new Root());
        when(rootService.findAllRoots()).thenReturn(mockRoots);

        ResponseEntity<List<Root>> response = controller.getAllRoots();

        assertEquals(mockRoots, response.getBody());
        assertEquals(ResponseEntity.ok(mockRoots), response);
        verify(rootService).findAllRoots();
    }

    @Test
    void findEquationsByRoot_ReturnsRootList() {
        double testRootValue = 1.0;
        List<Root> mockRoots = List.of(new Root(), new Root());
        when(rootService.findByRoot(testRootValue)).thenReturn(mockRoots);

        ResponseEntity<List<Root>> response = controller.findEquationsByRoot(testRootValue);

        assertEquals(mockRoots, response.getBody());
        assertEquals(ResponseEntity.ok(mockRoots), response);
        verify(rootService).findByRoot(testRootValue);
    }

    @Test
    void findEquationsByNumber_ReturnsRootList() {
        String testNumber = "2";
        List<Root> mockRoots = List.of(new Root(), new Root()); // Подготовка мок-данных
        when(rootService.findByNumber(testNumber)).thenReturn(mockRoots);

        ResponseEntity<List<Root>> response = controller.findEquationsByNumber(testNumber);

        assertEquals(mockRoots, response.getBody());
        assertEquals(ResponseEntity.ok(mockRoots), response);
        verify(rootService).findByNumber(testNumber);
    }

    @Test
    void saveEquationAndRoot_Success() {
        EquationDTO dto = new EquationDTO();

        ResponseEntity<?> response = controller.saveEquationAndRoot(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);

        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getBody();

        assertEquals("Equation correct and saved", responseBody.get("equationMessage"));
        assertEquals("The root are correct and preserved", responseBody.get("rootsMessage"));
    }

    @Test
    void saveEquationAndRoot_AlreadySavedException() {
        EquationDTO dto = new EquationDTO();
        doThrow(new AlreadySavedEquationException("Already saved")).when(equationService).checkAndSaveRoot(any(EquationDTO.class));

        ResponseEntity<?> response = controller.saveEquationAndRoot(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        assert responseBody != null;
        assertEquals("Already saved", responseBody.get("equationMessage"));
        assertEquals("The root are correct and preserved", responseBody.get("rootsMessage"));
    }

    @Test
    void saveEquationAndRoot_AlreadySavedRootException() {
        EquationDTO dto = new EquationDTO();
        doThrow(new AlreadySavedRootException("Root already saved")).when(equationService).checkAndSaveRoot(any(EquationDTO.class));

        ResponseEntity<?> response = controller.saveEquationAndRoot(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        assert responseBody != null;
        assertEquals("Equation correct", responseBody.get("equationMessage"));
        assertEquals("Root already saved", responseBody.get("rootsMessage"));
    }

    @Test
    void saveEquationAndRoot_EquationException() {
        EquationDTO dto = new EquationDTO();
        doThrow(new EquationException("Invalid equation")).when(equationService).checkAndSaveRoot(any(EquationDTO.class));

        ResponseEntity<?> response = controller.saveEquationAndRoot(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        assert responseBody != null;
        assertEquals("Invalid equation", responseBody.get("equationMessage"));
        assertEquals("No way to check root due to wrong equation", responseBody.get("rootsMessage"));
    }

    @Test
    void saveEquationAndRoot_RootException() {
        EquationDTO dto = new EquationDTO();
        doThrow(new RootException("Invalid root")).when(equationService).checkAndSaveRoot(any(EquationDTO.class));

        ResponseEntity<?> response = controller.saveEquationAndRoot(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        assert responseBody != null;
        assertEquals("Equation correct", responseBody.get("equationMessage"));
        assertEquals("Invalid root", responseBody.get("rootsMessage"));
    }

}

