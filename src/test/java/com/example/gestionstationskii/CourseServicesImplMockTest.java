package com.example.gestionstationskii;

import com.example.gestionstationskii.entities.Course;
import com.example.gestionstationskii.entities.Support;
import com.example.gestionstationskii.entities.TypeCourse;
import com.example.gestionstationskii.repositories.ICourseRepository;
import com.example.gestionstationskii.services.CourseServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServicesImplMockitoTest {

    @Mock
    private ICourseRepository courseRepository; // Mock repository

    @InjectMocks
    private CourseServicesImpl courseServices; // Inject mock into service

    private Course testCourse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mocks
        // Create a sample course to be used in the tests
        testCourse = new Course();
        testCourse.setNumCourse(1L);
        testCourse.setLevel(3);
        testCourse.setTypeCourse(TypeCourse.INDIVIDUAL); // assuming you have this enum
        testCourse.setSupport(Support.SKI); // assuming you have this enum
        testCourse.setPrice(120.5f);
        testCourse.setTimeSlot(4);
    }

    @Test
    void testRetrieveAllCourses() {
        // Arrange
        Course course2 = new Course();
        course2.setNumCourse(2L);
        course2.setLevel(2);
        List<Course> mockCourseList = Arrays.asList(testCourse, course2); // Use Arrays.asList instead of List.of()

        when(courseRepository.findAll()).thenReturn(mockCourseList); // Mock repository behavior

        // Act
        List<Course> retrievedCourses = courseServices.retrieveAllCourses();

        // Assert
        assertEquals(2, retrievedCourses.size());
        assertEquals(testCourse.getNumCourse(), retrievedCourses.get(0).getNumCourse());
        verify(courseRepository, times(1)).findAll(); // Ensure the repository method is called once
    }


    @Test
    void testAddCourse() {
        // Arrange
        when(courseRepository.save(testCourse)).thenReturn(testCourse); // Mock repository behavior for saving

        // Act
        Course savedCourse = courseServices.addCourse(testCourse);

        // Assert
        assertNotNull(savedCourse);
        assertEquals(1L, savedCourse.getNumCourse());
        verify(courseRepository, times(1)).save(testCourse); // Ensure save() is called once
    }

    @Test
    void testUpdateCourse() {
        // Arrange
        testCourse.setPrice(150.0f); // Change the price
        when(courseRepository.save(testCourse)).thenReturn(testCourse); // Mock the save behavior

        // Act
        Course updatedCourse = courseServices.updateCourse(testCourse);

        // Assert
        assertEquals(150.0f, updatedCourse.getPrice());
        verify(courseRepository, times(1)).save(testCourse); // Verify save was called
    }

    @Test
    void testRetrieveCourse() {
        // Arrange
        when(courseRepository.findById(1L)).thenReturn(Optional.of(testCourse)); // Mock repository behavior

        // Act
        Course retrievedCourse = courseServices.retrieveCourse(1L);

        // Assert
        assertNotNull(retrievedCourse);
        assertEquals(1L, retrievedCourse.getNumCourse());
        assertEquals(3, retrievedCourse.getLevel());
        verify(courseRepository, times(1)).findById(1L); // Verify findById was called once
    }

    @Test
    void testRetrieveCourse_NotFound() {
        // Arrange
        when(courseRepository.findById(1L)).thenReturn(Optional.empty()); // Simulate course not found

        // Act
        Course retrievedCourse = courseServices.retrieveCourse(1L);

        // Assert
        assertNull(retrievedCourse); // Course should be null if not found
        verify(courseRepository, times(1)).findById(1L); // Verify findById was called once
    }

    @Test
    public void testGetCoursesByLevelAndRating() {
        // Arrange
        Course course1 = new Course();
        course1.setNumCourse(1L);
        course1.setLevel(1);
        course1.setRating(4.5f); // Rating is above minRating

        Course course2 = new Course();
        course2.setNumCourse(2L);
        course2.setLevel(2);
        course2.setRating(3.5f); // Not in the level we're looking for

        Course course3 = new Course();
        course3.setNumCourse(3L);
        course3.setLevel(1);
        course3.setRating(4.0f); // Rating is equal to minRating

        // Mock the repository's findAll method
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2, course3));

        // Act
        List<Course> result = courseServices.getCoursesByLevelAndRating(1, 4.0f); // Searching for level 1 and minRating 4.0

        // Assert
        assertEquals(2, result.size()); // Expecting 2 courses that match criteria
        assertTrue(result.contains(course1)); // course1 should be in the result
        assertTrue(result.contains(course3)); // course3 should also be in the result
        assertFalse(result.contains(course2)); // course2 should not be in the result
    }





}

