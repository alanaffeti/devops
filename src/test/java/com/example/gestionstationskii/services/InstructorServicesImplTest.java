package com.example.gestionstationskii.services;

import com.example.gestionstationskii.entities.Course;
import com.example.gestionstationskii.entities.Instructor;
import com.example.gestionstationskii.repositories.ICourseRepository;
import com.example.gestionstationskii.repositories.IInstructorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InstructorServicesImplTest {

    @Mock
    private IInstructorRepository instructorRepository;

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private InstructorServicesImpl instructorServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addInstructor() {
        Instructor instructor = new Instructor();
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor result = instructorServices.addInstructor(instructor);

        assertEquals(instructor, result);
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void retrieveAllInstructors() {
        List<Instructor> instructors = Arrays.asList(new Instructor(), new Instructor());
        when(instructorRepository.findAll()).thenReturn(instructors);

        List<Instructor> result = instructorServices.retrieveAllInstructors();

        assertEquals(instructors, result);
        verify(instructorRepository, times(1)).findAll();
    }

    @Test
    void updateInstructor_InstructorExists() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);
        when(instructorRepository.existsById(1L)).thenReturn(true);
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor result = instructorServices.updateInstructor(instructor);

        assertEquals(instructor, result);
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void updateInstructor_InstructorDoesNotExist() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);
        when(instructorRepository.existsById(1L)).thenReturn(false);

        Instructor result = instructorServices.updateInstructor(instructor);

        assertNull(result);
        verify(instructorRepository, times(0)).save(instructor);
    }

    @Test
    void retrieveInstructor() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));

        Instructor result = instructorServices.retrieveInstructor(1L);

        assertEquals(instructor, result);
        verify(instructorRepository, times(1)).findById(1L);
    }

    @Test
    void addInstructorAndAssignToCourse_CourseExists() {
        Instructor instructor = new Instructor();
        Course course = new Course();
        course.setNumCourse(1L);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor result = instructorServices.addInstructorAndAssignToCourse(instructor, 1L);

        assertNotNull(result);
        assertTrue(result.getCourses().contains(course));
        verify(courseRepository, times(1)).findById(1L);
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void addInstructorAndAssignToCourse_CourseDoesNotExist() {
        Instructor instructor = new Instructor();
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Instructor result = instructorServices.addInstructorAndAssignToCourse(instructor, 1L);

        assertNull(result);
        verify(instructorRepository, times(0)).save(instructor);
    }

    @Test
    void findInstructorsByCourse_CourseExists() {
        Course course = new Course();
        course.setNumCourse(1L);
        List<Instructor> instructors = Arrays.asList(new Instructor(), new Instructor());
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(instructorRepository.findByCoursesContaining(course)).thenReturn(instructors);

        List<Instructor> result = instructorServices.findInstructorsByCourse(1L);

        assertEquals(instructors, result);
        verify(courseRepository, times(1)).findById(1L);
        verify(instructorRepository, times(1)).findByCoursesContaining(course);
    }

    @Test
    void findInstructorsByCourse_CourseDoesNotExist() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        List<Instructor> result = instructorServices.findInstructorsByCourse(1L);

        assertTrue(result.isEmpty());
        verify(courseRepository, times(1)).findById(1L);
        verify(instructorRepository, times(0)).findByCoursesContaining(any());
    }

    @Test
    void deleteInstructor_InstructorExists() {
        Long instructorId = 1L;
        when(instructorRepository.existsById(instructorId)).thenReturn(true);

        instructorServices.deleteInstructor(instructorId);

        verify(instructorRepository, times(1)).deleteById(instructorId);
    }

    @Test
    void deleteInstructor_InstructorDoesNotExist() {
        Long instructorId = 1L;
        when(instructorRepository.existsById(instructorId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> instructorServices.deleteInstructor(instructorId));
        verify(instructorRepository, times(0)).deleteById(instructorId);
    }

    @Test
    void assignCourseToInstructor_BothExist() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);
        Course course = new Course();
        course.setNumCourse(2L);
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
        when(courseRepository.findById(2L)).thenReturn(Optional.of(course));
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor result = instructorServices.assignCourseToInstructor(1L, 2L);

        assertNotNull(result);
        assertTrue(result.getCourses().contains(course));
        verify(courseRepository, times(1)).findById(2L);
        verify(instructorRepository, times(1)).save(instructor);
    }




}
