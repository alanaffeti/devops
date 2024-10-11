package com.example.gestionstationskii;

import com.example.gestionstationskii.entities.Course;
import com.example.gestionstationskii.entities.Instructor;
import com.example.gestionstationskii.repositories.IInstructorRepository;
import com.example.gestionstationskii.repositories.ICourseRepository;
import com.example.gestionstationskii.services.InstructorServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InstructorServicesImplTest {

    private InstructorServicesImpl instructorServices;
    private IInstructorRepository instructorRepository;
    private ICourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        instructorRepository = mock(IInstructorRepository.class);
        courseRepository = mock(ICourseRepository.class);
        instructorServices = new InstructorServicesImpl(instructorRepository, courseRepository);
    }

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

    @Test
    void testRetrieveInstructor() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);
        instructor.setFirstName("Jane");
        instructor.setLastName("Doe");

        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));

        Instructor foundInstructor = instructorServices.retrieveInstructor(1L);
        assertNotNull(foundInstructor);
        assertEquals(1L, foundInstructor.getNumInstructor());
        assertEquals("Jane", foundInstructor.getFirstName());
        assertEquals("Doe", foundInstructor.getLastName());
    }

    @Test
    void testRetrieveInstructorNotFound() {
        when(instructorRepository.findById(2L)).thenReturn(Optional.empty());

        Instructor foundInstructor = instructorServices.retrieveInstructor(2L);
        assertNull(foundInstructor);
    }

    @Test
    void testUpdateInstructor() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);
        instructor.setFirstName("John");
        instructor.setLastName("Doe");

        when(instructorRepository.existsById(1L)).thenReturn(true);
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor updatedInstructor = instructorServices.updateInstructor(instructor);
        assertNotNull(updatedInstructor);
        assertEquals("John", updatedInstructor.getFirstName());
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void testUpdateInstructorNotFound() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(2L);

        when(instructorRepository.existsById(2L)).thenReturn(false);

        Instructor updatedInstructor = instructorServices.updateInstructor(instructor);
        assertNull(updatedInstructor);
    }

    @Test
    void testDeleteInstructor() {
        when(instructorRepository.existsById(1L)).thenReturn(true);

        instructorServices.deleteInstructor(1L);
        verify(instructorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteInstructorNotFound() {
        when(instructorRepository.existsById(2L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            instructorServices.deleteInstructor(2L);
        });
        assertEquals("Instructor not found", exception.getMessage());
    }

    @Test
    void testAddInstructorAndAssignToCourse() {
        Instructor instructor = new Instructor();
        instructor.setFirstName("Alice");
        instructor.setLastName("Smith");

        Course course = new Course();
        course.setNumCourse(1L); // Corrected this line

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor savedInstructor = instructorServices.addInstructorAndAssignToCourse(instructor, 1L);
        assertNotNull(savedInstructor);
        assertTrue(savedInstructor.getCourses().contains(course));
        verify(instructorRepository, times(1)).save(instructor);
    }


    @Test
    void testAddInstructorAndAssignToCourseCourseNotFound() {
        Instructor instructor = new Instructor();
        instructor.setFirstName("Bob");
        instructor.setLastName("Johnson");

        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Instructor savedInstructor = instructorServices.addInstructorAndAssignToCourse(instructor, 1L);
        assertNull(savedInstructor);
    }

    @Test
    void testAssignCourseToInstructor() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);
        instructor.setCourses(new HashSet<>());

        Course course = new Course();
        course.setNumCourse(1L); // Corrected this line

        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor updatedInstructor = instructorServices.assignCourseToInstructor(1L, 1L);
        assertNotNull(updatedInstructor);
        assertTrue(updatedInstructor.getCourses().contains(course));
        verify(instructorRepository, times(1)).save(instructor);
    }


    @Test
    void testAssignCourseToInstructorInstructorNotFound() {
        when(instructorRepository.findById(1L)).thenReturn(Optional.empty());

        Instructor updatedInstructor = instructorServices.assignCourseToInstructor(1L, 1L);
        assertNull(updatedInstructor);
    }

    @Test
    void testAssignCourseToInstructorCourseNotFound() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);

        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Instructor updatedInstructor = instructorServices.assignCourseToInstructor(1L, 1L);
        assertNull(updatedInstructor);
    }

    @Test
    void testFindInstructorsByCourse() {
        Course course = new Course();
        course.setNumCourse(1L);

        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);
        instructor.setCourses(new HashSet<>());
        instructor.getCourses().add(course);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(instructorRepository.findByCoursesContaining(course)).thenReturn(Arrays.asList(instructor)); // Use Arrays.asList

        List<Instructor> instructors = instructorServices.findInstructorsByCourse(1L);
        assertFalse(instructors.isEmpty());
        assertEquals(1, instructors.size());
        assertEquals(instructor, instructors.get(0)); // Or check specific properties
    }

    @Test
    void testFindInstructorsByCourseCourseNotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        List<Instructor> instructors = instructorServices.findInstructorsByCourse(1L);
        assertTrue(instructors.isEmpty());
    }
}
