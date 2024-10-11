package com.example.gestionstationskii;

import com.example.gestionstationskii.entities.Course;
import com.example.gestionstationskii.entities.Support;
import com.example.gestionstationskii.entities.TypeCourse;
import com.example.gestionstationskii.repositories.ICourseRepository;
import com.example.gestionstationskii.services.ICourseServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest // Load full Spring context for integration testing
@Transactional // Ensure each test method runs in a transaction that rolls back afterward
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.class)
class CourseServicesImplUnitTest {

    @Autowired
    private ICourseServices courseServices; // Autowire your service

    @Autowired
    private ICourseRepository courseRepository; // Autowire repository for test data setup

    private Course testCourse;

    @BeforeEach
    void setUp() {
        // Set up a sample Course before each test
        testCourse = new Course();
        testCourse.setLevel(3);
        testCourse.setTypeCourse(TypeCourse.INDIVIDUAL); // assuming enum values
        testCourse.setSupport(Support.SKI);
        testCourse.setPrice(120.5f);
        testCourse.setTimeSlot(4);
        courseRepository.save(testCourse); // Save the course in the DB for testing
    }

    @Test
    void testRetrieveAllCourses() {
        // Act
        List<Course> courseList = courseServices.retrieveAllCourses();

        // Assert
        assertFalse(courseList.isEmpty()); // Ensure list is not empty
        assertEquals(testCourse.getNumCourse(), courseList.get(0).getNumCourse());
        assertEquals(3, courseList.get(0).getLevel()); // Test a field value
    }

    @Test
    void testAddCourse() {
        // Arrange
        Course newCourse = new Course();
        newCourse.setLevel(2);
        newCourse.setTypeCourse(TypeCourse.INDIVIDUAL);
        newCourse.setSupport(Support.SKI);
        newCourse.setPrice(99.99f);
        newCourse.setTimeSlot(5);

        // Act
        Course savedCourse = courseServices.addCourse(newCourse);

        // Assert
        assertNotNull(savedCourse.getNumCourse());
        assertEquals(2, savedCourse.getLevel());
    }

    @Test
    void testUpdateCourse() {
        // Arrange
        testCourse.setPrice(150.0f); // Update some field

        // Act
        Course updatedCourse = courseServices.updateCourse(testCourse);

        // Assert
        assertEquals(150.0f, updatedCourse.getPrice());
        assertEquals(testCourse.getNumCourse(), updatedCourse.getNumCourse()); // Ensure same entity is updated
    }

    @Test
    void testRetrieveCourse() {
        // Act
        Course retrievedCourse = courseServices.retrieveCourse(testCourse.getNumCourse());

        // Assert
        assertNotNull(retrievedCourse);
        assertEquals(testCourse.getNumCourse(), retrievedCourse.getNumCourse());
        assertEquals(testCourse.getLevel(), retrievedCourse.getLevel());
    }
}
