package com.example.gestionstationskii;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import com.example.gestionstationskii.repositories.ICourseRepository;
import com.example.gestionstationskii.repositories.IPisteRepository;
import com.example.gestionstationskii.repositories.IRegistrationRepository;
import com.example.gestionstationskii.repositories.ISkierRepository;
import com.example.gestionstationskii.repositories.ISubscriptionRepository;
import com.example.gestionstationskii.services.SkierServicesImpl;


public class SkierServicesImplTest {
    private SkierServicesImpl skierServices;
    private ISkierRepository skierRepository;
    private IPisteRepository pisteRepository;
    private ICourseRepository courseRepository;
    private IRegistrationRepository registrationRepository;
    private ISubscriptionRepository subscriptionRepository;

    public SkierServicesImplTest() {
    }

    @BeforeEach
    public void setUp() {
        this.skierRepository = (ISkierRepository) Mockito.mock(ISkierRepository.class);
        this.pisteRepository = (IPisteRepository)Mockito.mock(IPisteRepository.class);
        this.courseRepository = (ICourseRepository)Mockito.mock(ICourseRepository.class);
        this.registrationRepository = (IRegistrationRepository)Mockito.mock(IRegistrationRepository.class);
        this.subscriptionRepository = (ISubscriptionRepository)Mockito.mock(ISubscriptionRepository.class);
        this.skierServices = new SkierServicesImpl(this.skierRepository, this.pisteRepository, this.courseRepository, this.registrationRepository, this.subscriptionRepository);
    }



}
