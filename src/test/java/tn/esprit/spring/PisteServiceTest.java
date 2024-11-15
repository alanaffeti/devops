package tn.esprit.spring;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.PisteServicesImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class PisteServiceTest {

    @InjectMocks
    PisteServicesImpl pisteServices;

    @Mock
    IPisteRepository pisteRepository;

    private Piste piste;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        piste = new Piste();
        piste.setNumPiste(1L);
        piste.setNamePiste("Test Piste");
        piste.setLength(500);
        piste.setSlope(30);
    }

    @Test
    void testAddPiste() {
        // Arrange
        when(pisteRepository.save(piste)).thenReturn(piste);

        // Act
        Piste result = pisteServices.addPiste(piste);

        // Assert
        assertEquals(piste, result);
        verify(pisteRepository, times(1)).save(piste);
    }

    @Test
    void testRetrieveAllPistes() {
        // Arrange
        List<Piste> pistes = Arrays.asList(piste);
        when(pisteRepository.findAll()).thenReturn(pistes);

        // Act
        List<Piste> result = pisteServices.retrieveAllPistes();

        // Assert
        assertEquals(1, result.size());
        assertEquals(piste, result.get(0));
        verify(pisteRepository, times(1)).findAll();
    }

    @Test
    void testRemovePiste() {
        // Act
        pisteServices.removePiste(1L);

        // Assert
        verify(pisteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testRetrievePiste() {
        // Arrange
        when(pisteRepository.findById(1L)).thenReturn(Optional.of(piste));

        // Act
        Piste result = pisteServices.retrievePiste(1L);

        // Assert
        assertEquals(piste, result);
        verify(pisteRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdatePiste() {
        // Arrange
        Piste updatedPiste = new Piste();
        updatedPiste.setNamePiste("Updated Piste");
        updatedPiste.setLength(600);
        updatedPiste.setSlope(35);

        when(pisteRepository.findById(1L)).thenReturn(Optional.of(piste));  // Simule la récupération de la piste originale
        when(pisteRepository.save(piste)).thenReturn(piste);  // Simule la sauvegarde de la piste modifiée

        // Act
        Piste result = pisteServices.updatePiste(1L, updatedPiste);

        // Assert
        assertNotNull(result);  // Vérifie que le résultat n'est pas nul
        assertEquals("Updated Piste", result.getNamePiste());  // Vérifie que le nom a été mis à jour
        assertEquals(600, result.getLength());  // Vérifie que la longueur a été mise à jour
        assertEquals(35, result.getSlope());  // Vérifie que la pente a été mise à jour

        // Vérifie que la méthode save a bien été appelée
        verify(pisteRepository, times(1)).save(piste);
        // Vérifie que la méthode findById a bien été appelée
        verify(pisteRepository, times(1)).findById(1L);
    }
    @Test
    void testRetrievePistesByColor() {
        // Arrange
        Piste piste1 = new Piste();
        piste1.setNumPiste(1L);
        piste1.setNamePiste("Blue Piste");
        piste1.setColor(Color.BLUE);
        piste1.setLength(400);
        piste1.setSlope(20);

        Piste piste2 = new Piste();
        piste2.setNumPiste(2L);
        piste2.setNamePiste("Green Piste");
        piste2.setColor(Color.GREEN);
        piste2.setLength(300);
        piste2.setSlope(15);

        List<Piste> pistesByColor = Arrays.asList(piste1);
        when(pisteRepository.findByColor(Color.BLUE)).thenReturn(pistesByColor);

        // Act
        List<Piste> result = pisteServices.retrievePistesByColor(Color.BLUE);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Blue Piste", result.get(0).getNamePiste());
        verify(pisteRepository, times(1)).findByColor(Color.BLUE);
    }

    @Test
    void testCalculateAverageSlope() {
        // Arrange
        Piste piste1 = new Piste();
        piste1.setNumPiste(1L);
        piste1.setNamePiste("Piste A");
        piste1.setSlope(20);

        Piste piste2 = new Piste();
        piste2.setNumPiste(2L);
        piste2.setNamePiste("Piste B");
        piste2.setSlope(40);

        List<Piste> pistes = Arrays.asList(piste1, piste2);
        when(pisteRepository.findAll()).thenReturn(pistes);

        // Act
        double averageSlope = pisteServices.calculateAverageSlope();

        // Assert
        assertEquals(30.0, averageSlope);
        verify(pisteRepository, times(1)).findAll();
    }

}
