package com.example.gestionstationskii;

import com.example.gestionstationskii.entities.Course;
import com.example.gestionstationskii.entities.Instructor;
import com.example.gestionstationskii.repositories.IInstructorRepository;
import com.example.gestionstationskii.repositories.ICourseRepository;
import com.example.gestionstationskii.services.InstructorServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class InstructorServicesImplTest {
    /*

    private InstructorServicesImpl instructorServices;
    private IInstructorRepository instructorRepository;
    private ICourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        instructorRepository = mock(IInstructorRepository.class);
        courseRepository = mock(ICourseRepository.class);
        instructorServices = new InstructorServicesImpl(instructorRepository, courseRepository);
    }

    // Test 201 Created - Adding an instructor
    @Test
    void testAddInstructor() {
        Instructor instructor = new Instructor();
        instructor.setFirstName("John");
        instructor.setLastName("Doe");

        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor savedInstructor = instructorServices.addInstructor(instructor);
        assertNotNull(savedInstructor);
        assertEquals("John", savedInstructor.getFirstName());
        assertEquals("Doe", savedInstructor.getLastName());
        verify(instructorRepository, times(1)).save(instructor);
    }

    // Test 404 Not Found - Retrieve instructor that doesn't exist
    @Test
    void testRetrieveInstructorNotFound() {
        when(instructorRepository.findById(2L)).thenReturn(Optional.empty());

        Instructor foundInstructor = instructorServices.retrieveInstructor(2L);
        assertNull(foundInstructor);
    }

    // Test 204 No Content - Successfully delete an instructor
    @Test
    void testDeleteInstructor() {
        when(instructorRepository.existsById(1L)).thenReturn(true);

        instructorServices.deleteInstructor(1L);
        verify(instructorRepository, times(1)).deleteById(1L);
    }

    // Test 404 Not Found - Deleting a non-existent instructor
    @Test
    void testDeleteInstructorNotFound() {
        when(instructorRepository.existsById(2L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            instructorServices.deleteInstructor(2L);
        });
        assertEquals("Instructor not found", exception.getMessage());
    }

    // Test 400 Bad Request - Update instructor with invalid data
    @Test
    void testUpdateInstructorInvalidData() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(null); // Invalid ID

        when(instructorRepository.existsById(anyLong())).thenReturn(false);

        Instructor updatedInstructor = instructorServices.updateInstructor(instructor);
        assertNull(updatedInstructor);
    }

    // Test 404 Not Found - Update non-existent instructor
    @Test
    void testUpdateInstructorNotFound() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(2L);

        when(instructorRepository.existsById(2L)).thenReturn(false);

        Instructor updatedInstructor = instructorServices.updateInstructor(instructor);
        assertNull(updatedInstructor);
    }

    // Test 201 Created - Adding instructor and assigning to a course
    @Test
    void testAddInstructorAndAssignToCourse() {
        Instructor instructor = new Instructor();
        instructor.setFirstName("Alice");
        instructor.setLastName("Smith");

        Course course = new Course();
        course.setNumCourse(1L);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor savedInstructor = instructorServices.addInstructorAndAssignToCourse(instructor, 1L);
        assertNotNull(savedInstructor);
        assertTrue(savedInstructor.getCourses().contains(course));
        verify(instructorRepository, times(1)).save(instructor);
    }

    // Test 404 Not Found - Assign to non-existent course
    @Test
    void testAddInstructorAndAssignToCourseCourseNotFound() {
        Instructor instructor = new Instructor();
        instructor.setFirstName("Bob");
        instructor.setLastName("Johnson");

        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Instructor savedInstructor = instructorServices.addInstructorAndAssignToCourse(instructor, 1L);
        assertNull(savedInstructor);
    }

    // Test 404 Not Found - Assign course to non-existent instructor
    @Test
    void testAssignCourseToInstructorInstructorNotFound() {
        when(instructorRepository.findById(1L)).thenReturn(Optional.empty());

        Instructor updatedInstructor = instructorServices.assignCourseToInstructor(1L, 1L);
        assertNull(updatedInstructor);
    }

    // Test 404 Not Found - Assign non-existent course to instructor
    @Test
    void testAssignCourseToInstructorCourseNotFound() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);

        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Instructor updatedInstructor = instructorServices.assignCourseToInstructor(1L, 1L);
        assertNull(updatedInstructor);
    }

    // Test 404 Not Found - Find instructors by non-existent course
    @Test
    void testFindInstructorsByCourseCourseNotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        List<Instructor> instructors = instructorServices.findInstructorsByCourse(1L);
        assertTrue(instructors.isEmpty());
    }*/
}
