package com.example.gestionstationskii.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.gestionstationskii.entities.*;
import com.example.gestionstationskii.repositories.*;


import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
@Slf4j
@AllArgsConstructor
@Service
public class RegistrationServicesImpl implements  IRegistrationServices{

    private IRegistrationRepository registrationRepository;
    private ISkierRepository skierRepository;
    private ICourseRepository courseRepository;


    @Override
    public Registration addRegistrationAndAssignToSkier(Registration registration, Long numSkier) {
        Skier skier = skierRepository.findById(numSkier).orElse(null);
        registration.setSkier(skier);
        return registrationRepository.save(registration);
    }

    @Override
    public Registration assignRegistrationToCourse(Long numRegistration, Long numCourse) {
        Registration registration = registrationRepository.findById(numRegistration).orElse(null);
        Course course = courseRepository.findById(numCourse).orElse(null);
        registration.setCourse(course);
        return registrationRepository.save(registration);
    }

    @Transactional
    @Override
    public Registration addRegistrationAndAssignToSkierAndCourse(Registration registration, Long numSkieur, Long numCours) {
        Skier skier = skierRepository.findById(numSkieur).orElse(null);
        Course course = courseRepository.findById(numCours).orElse(null);

        if (skier == null || course == null) {
            return null;
        }

        if (isDuplicateRegistration(registration.getNumWeek(), skier.getNumSkier(), course.getNumCourse())) {
            log.info("Sorry, you're already registered for this course in week: " + registration.getNumWeek());
            return null;
        }

        int ageSkieur = calculateAge(skier.getDateOfBirth());
        log.info("Age " + ageSkieur);

        switch (course.getTypeCourse()) {
            case INDIVIDUAL:
                log.info("Adding registration without age or capacity checks.");
                return assignRegistration(registration, skier, course);

            case COLLECTIVE_CHILDREN:
                return handleCollectiveChildrenCourse(registration, skier, course, ageSkieur);

            default:
                return handleCollectiveAdultCourse(registration, skier, course, ageSkieur);
        }
    }

    // Check for existing registration in the same week
    private boolean isDuplicateRegistration(int numWeek, Long numSkieur, Long numCourse) {
        return registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(numWeek, numSkieur, numCourse) >= 1;
    }

    // Calculate the age of the skier
    private int calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    // Assign registration with logging
    private Registration assignRegistration(Registration registration, Skier skier, Course course) {
        registration.setSkier(skier);
        registration.setCourse(course);
        return registrationRepository.save(registration);
    }

    // Handle registration for Collective Children course
    private Registration handleCollectiveChildrenCourse(Registration registration, Skier skier, Course course, int ageSkieur) {
        if (ageSkieur < 16) {
            log.info("Eligible for CHILD course.");
            if (isCourseNotFull(course, registration.getNumWeek())) {
                log.info("Course successfully added!");
                return assignRegistration(registration, skier, course);
            } else {
                log.info("Full Course! Please choose another week to register.");
                return null;
            }
        } else {
            log.info("Age restriction: Not eligible for CHILD course. Try a Collective Adult Course.");
            return null;
        }
    }

    // Handle registration for Collective Adult course
    private Registration handleCollectiveAdultCourse(Registration registration, Skier skier, Course course, int ageSkieur) {
        if (ageSkieur >= 16) {
            log.info("Eligible for ADULT course.");
            if (isCourseNotFull(course, registration.getNumWeek())) {
                log.info("Course successfully added!");
                return assignRegistration(registration, skier, course);
            } else {
                log.info("Full Course! Please choose another week to register.");
                return null;
            }
        } else {
            log.info("Age restriction: Not eligible for ADULT course. Try a Collective Child Course.");
            return null;
        }
    }

    // Check if course has reached capacity for the week
    private boolean isCourseNotFull(Course course, int numWeek) {
        return registrationRepository.countByCourseAndNumWeek(course, numWeek) < 6;
    }


    @Override
    public List<Integer> numWeeksCourseOfInstructorBySupport(Long numInstructor, Support support) {
        return registrationRepository.numWeeksCourseOfInstructorBySupport(numInstructor, support);
    }

}
