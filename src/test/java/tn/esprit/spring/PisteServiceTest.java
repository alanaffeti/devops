package tn.esprit.spring;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
}
