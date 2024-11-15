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
    private ICourseRepository courseRepository;

    @InjectMocks
    private CourseServicesImpl courseServices;

    private Course testCourse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); 

        testCourse = new Course();
        testCourse.setNumCourse(1L);
        testCourse.setLevel(3);
        testCourse.setTypeCourse(TypeCourse.INDIVIDUAL);
        testCourse.setSupport(Support.SKI);
        testCourse.setPrice(120.5f);
        testCourse.setTimeSlot(4);
    }

    @Test
    void testRetrieveAllCourses() {
       
        Course course2 = new Course();
        course2.setNumCourse(2L);
        course2.setLevel(2);
        List<Course> mockCourseList = Arrays.asList(testCourse, course2);

        when(courseRepository.findAll()).thenReturn(mockCourseList);

       
        List<Course> retrievedCourses = courseServices.retrieveAllCourses();

        
        assertEquals(2, retrievedCourses.size());
        assertEquals(testCourse.getNumCourse(), retrievedCourses.get(0).getNumCourse());
        verify(courseRepository, times(1)).findAll(); 
    }


    @Test
    void testAddCourse() {
        
        when(courseRepository.save(testCourse)).thenReturn(testCourse);
        
        Course savedCourse = courseServices.addCourse(testCourse);

        
        assertNotNull(savedCourse);
        assertEquals(1L, savedCourse.getNumCourse());
        verify(courseRepository, times(1)).save(testCourse);
    }

    @Test
    void testUpdateCourse() {
        
        testCourse.setPrice(150.0f);
        when(courseRepository.save(testCourse)).thenReturn(testCourse);

       
        Course updatedCourse = courseServices.updateCourse(testCourse);

       
        assertEquals(150.0f, updatedCourse.getPrice());
        verify(courseRepository, times(1)).save(testCourse);
    }

    @Test
    void testRetrieveCourse() {
       
        when(courseRepository.findById(1L)).thenReturn(Optional.of(testCourse));

        
        Course retrievedCourse = courseServices.retrieveCourse(1L);

        
        assertNotNull(retrievedCourse);
        assertEquals(1L, retrievedCourse.getNumCourse());
        assertEquals(3, retrievedCourse.getLevel());
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveCourse_NotFound() {
        
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        
        Course retrievedCourse = courseServices.retrieveCourse(1L);

        
        assertNull(retrievedCourse); 
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetCoursesByLevelAndRating() {
        
        Course course1 = new Course();
        course1.setNumCourse(1L);
        course1.setLevel(1);
        course1.setRating(4.5f); 

        Course course2 = new Course();
        course2.setNumCourse(2L);
        course2.setLevel(2);
        course2.setRating(3.5f); 

        Course course3 = new Course();
        course3.setNumCourse(3L);
        course3.setLevel(1);
        course3.setRating(4.0f); 

        
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2, course3));

       
        List<Course> result = courseServices.getCoursesByLevelAndRating(1, 4.0f); 

       
        assertEquals(2, result.size());
        assertTrue(result.contains(course1));
        assertTrue(result.contains(course3));
        assertFalse(result.contains(course2));
    }


    @Test
    public void testGetAdvancedFilteredCourses() {
       
        Course course1 = new Course(1L, 1, TypeCourse.INDIVIDUAL, Support.SKI, 100f, 4, 4.5f);
        Course course2 = new Course(2L, 1, TypeCourse.COLLECTIVE_CHILDREN, Support.SNOWBOARD, 150f, 5, 3.8f);
        Course course3 = new Course(3L, 1, TypeCourse.INDIVIDUAL, Support.SKI, 120f, 4, 4.0f);

        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2, course3));

      
        List<Course> filteredCourses = courseServices.getAdvancedFilteredCourses(1, TypeCourse.INDIVIDUAL, Support.SKI, 4.0f);

      
        assertEquals(2, filteredCourses.size());
        assertTrue(filteredCourses.contains(course1));
        assertTrue(filteredCourses.contains(course3));
        assertFalse(filteredCourses.contains(course2)); 
        verify(courseRepository, times(1)).findAll();
    }



}

