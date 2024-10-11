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

    @Test
    void testRetrieveAllInstructors() {
        Instructor instructor1 = new Instructor();
        Instructor instructor2 = new Instructor();

        when(instructorRepository.findAll()).thenReturn(Arrays.asList(instructor1, instructor2));

        List<Instructor> instructors = instructorServices.retrieveAllInstructors();

        assertEquals(2, instructors.size());
        verify(instructorRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveInstructor() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));

        Instructor foundInstructor = instructorServices.retrieveInstructor(1L);

        assertNotNull(foundInstructor);
        assertEquals(1L, foundInstructor.getNumInstructor());
        verify(instructorRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveInstructorNotFound() {
        when(instructorRepository.findById(2L)).thenReturn(Optional.empty());

        Instructor foundInstructor = instructorServices.retrieveInstructor(2L);

        assertNull(foundInstructor);
        verify(instructorRepository, times(1)).findById(2L);
    }

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

    @Test
    void testUpdateInstructorNotFound() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);

        when(instructorRepository.existsById(1L)).thenReturn(false);

        Instructor updatedInstructor = instructorServices.updateInstructor(instructor);

        assertNull(updatedInstructor);
        verify(instructorRepository, never()).save(instructor);
    }

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

    @Test
    void testDeleteInstructor() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);

        when(instructorRepository.existsById(1L)).thenReturn(true);

        instructorServices.deleteInstructor(1L);

        verify(instructorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteInstructorNotFound() {
        when(instructorRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            instructorServices.deleteInstructor(1L);
        });

        verify(instructorRepository, never()).deleteById(1L);
    }

    @Test
    void testFindInstructorsByCourse() {
        Course course = new Course();
        course.setNumCourse(1L);

        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);
        instructor.setCourses(new HashSet<>(Arrays.asList(course)));

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(instructorRepository.findByCoursesContaining(course)).thenReturn(Arrays.asList(instructor));

        List<Instructor> instructors = instructorServices.findInstructorsByCourse(1L);

        assertNotNull(instructors);
        assertEquals(1, instructors.size());
        verify(instructorRepository, times(1)).findByCoursesContaining(course);
    }

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
