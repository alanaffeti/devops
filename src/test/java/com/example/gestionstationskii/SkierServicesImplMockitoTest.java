package com.example.gestionstationskii;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.example.gestionstationskii.entities.Skier;
import com.example.gestionstationskii.repositories.ISkierRepository;
import com.example.gestionstationskii.services.SkierServicesImpl;
import java.util.Optional;


public class SkierServicesImplMockitoTest {
    @Mock
    private ISkierRepository skierRepository;
    @InjectMocks
    private SkierServicesImpl skierServices;

    public SkierServicesImplMockitoTest() {
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveSkier() {
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        skier.setFirstName("John");
        skier.setLastName("Doe");
        Mockito.when(this.skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        Skier retrievedSkier = this.skierServices.retrieveSkier(1L);
        Assertions.assertNotNull(retrievedSkier);
        Assertions.assertEquals("John", retrievedSkier.getFirstName());
        ((ISkierRepository)Mockito.verify(this.skierRepository, Mockito.times(1))).findById(1L);
    }

}
