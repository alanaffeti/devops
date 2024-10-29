package com.example.gestionstationskii;

import com.example.gestionstationskii.entities.Course;
import com.example.gestionstationskii.entities.Instructor;
import com.example.gestionstationskii.repositories.ICourseRepository;
import com.example.gestionstationskii.repositories.IInstructorRepository;
import com.example.gestionstationskii.services.InstructorServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InstructorServicesMockitoTest {

    @InjectMocks
    private InstructorServicesImpl instructorServices;

    @Mock
    private IInstructorRepository instructorRepository;

    @Mock
    private ICourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test 201 Created - Adding an instructor and assigning to course
    @Test
    void testAddInstructorAndAssignToCourse() {
        Instructor instructor = new Instructor();
        instructor.setFirstName("John Doe");

        Course course = new Course();
        course.setNumCourse(1L);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor savedInstructor = instructorServices.addInstructorAndAssignToCourse(instructor, 1L);

        assertNotNull(savedInstructor);
        assertTrue(savedInstructor.getCourses().contains(course));
        verify(courseRepository, times(1)).findById(1L);
        verify(instructorRepository, times(1)).save(instructor);
    }

    // Test 404 Not Found - Add instructor and assign to non-existent course
    @Test
    void testAddInstructorAndAssignToCourseNotFound() {
        Instructor instructor = new Instructor();
        instructor.setFirstName("John Doe");

        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Instructor savedInstructor = instructorServices.addInstructorAndAssignToCourse(instructor, 1L);

        assertNull(savedInstructor);
        verify(courseRepository, times(1)).findById(1L);
        verify(instructorRepository, never()).save(instructor);
    }

    // Test 204 No Content - Retrieve all instructors (expecting list is not empty)
    @Test
    void testRetrieveAllInstructors() {
        Instructor instructor1 = new Instructor();
        Instructor instructor2 = new Instructor();

        when(instructorRepository.findAll()).thenReturn(Arrays.asList(instructor1, instructor2));

        List<Instructor> instructors = instructorServices.retrieveAllInstructors();

        assertEquals(2, instructors.size());
        verify(instructorRepository, times(1)).findAll();
    }

    // Test 404 Not Found - Retrieve instructor that doesn't exist
    @Test
    void testRetrieveInstructorNotFound() {
        when(instructorRepository.findById(2L)).thenReturn(Optional.empty());

        Instructor foundInstructor = instructorServices.retrieveInstructor(2L);

        assertNull(foundInstructor);
        verify(instructorRepository, times(1)).findById(2L);
    }

    // Test 201 Created - Updating an existing instructor
    @Test
    void testUpdateInstructor() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);

        when(instructorRepository.existsById(1L)).thenReturn(true);
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor updatedInstructor = instructorServices.updateInstructor(instructor);

        assertNotNull(updatedInstructor);
        verify(instructorRepository, times(1)).save(instructor);
    }

    // Test 400 Bad Request - Updating an instructor with invalid data
    @Test
    void testUpdateInstructorInvalidData() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(null); // Invalid ID

        Instructor updatedInstructor = instructorServices.updateInstructor(instructor);

        assertNull(updatedInstructor);
        verify(instructorRepository, never()).save(instructor);
    }

    // Test 204 No Content - Delete an existing instructor
    @Test
    void testDeleteInstructor() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);

        when(instructorRepository.existsById(1L)).thenReturn(true);

        instructorServices.deleteInstructor(1L);

        verify(instructorRepository, times(1)).deleteById(1L);
    }

    // Test 404 Not Found - Delete non-existent instructor
    @Test
    void testDeleteInstructorNotFound() {
        when(instructorRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            instructorServices.deleteInstructor(1L);
        });

        verify(instructorRepository, never()).deleteById(1L);
    }

    // Test 201 Created - Assign course to instructor
    @Test
    void testAssignCourseToInstructor() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);
        instructor.setCourses(new HashSet<>());

        Course course = new Course();
        course.setNumCourse(1L);

        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor updatedInstructor = instructorServices.assignCourseToInstructor(1L, 1L);

        assertNotNull(updatedInstructor);
        assertTrue(updatedInstructor.getCourses().contains(course));
        verify(instructorRepository, times(1)).save(instructor);
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
        verify(courseRepository, times(1)).findById(1L);
    }

    // Test 404 Not Found - Assign course to non-existent instructor
    @Test
    void testAssignCourseToInstructorInstructorNotFound() {
        Course course = new Course();
        course.setNumCourse(1L);

        when(instructorRepository.findById(1L)).thenReturn(Optional.empty());

        Instructor updatedInstructor = instructorServices.assignCourseToInstructor(1L, 1L);

        assertNull(updatedInstructor);
        verify(instructorRepository, times(1)).findById(1L);
    }

    // Test 404 Not Found - Find instructors by non-existent course
    @Test
    void testFindInstructorsByCourseNotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        List<Instructor> instructors = instructorServices.findInstructorsByCourse(1L);

        assertTrue(instructors.isEmpty());
        verify(courseRepository, times(1)).findById(1L);
    }

    // Test 201 Created - Assign instructor to multiple courses
    @Test
    void testAssignInstructorsToMultipleCourses() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);
        Set<Long> courseIds = new HashSet<>(Arrays.asList(1L, 2L));

        Course course1 = new Course();
        course1.setNumCourse(1L);
        Course course2 = new Course();
        course2.setNumCourse(2L);

        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course1));
        when(courseRepository.findById(2L)).thenReturn(Optional.of(course2));
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor updatedInstructor = instructorServices.assignInstructorsToMultipleCourses(1L, courseIds);

        assertNotNull(updatedInstructor);
        assertEquals(2, updatedInstructor.getCourses().size());
        verify(instructorRepository, times(1)).save(instructor);
    }
}
