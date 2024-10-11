package com.example.gestionstationskii.services;

import com.example.gestionstationskii.entities.*;
import com.example.gestionstationskii.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class InstructorServicesImpl implements IInstructorServices {

    private final IInstructorRepository instructorRepository;
    private final ICourseRepository courseRepository;

    @Override
    public Instructor addInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @Override
    public List<Instructor> retrieveAllInstructors() {
        return instructorRepository.findAll();
    }

    @Override
    public Instructor updateInstructor(Instructor instructor) {
        if (instructorRepository.existsById(instructor.getNumInstructor())) {
            return instructorRepository.save(instructor);
        }
        return null; // or throw an exception
    }

    @Override
    public Instructor retrieveInstructor(Long numInstructor) {
        return instructorRepository.findById(numInstructor).orElse(null);
    }

    @Override
    public Instructor addInstructorAndAssignToCourse(Instructor instructor, Long numCourse) {
        Course course = courseRepository.findById(numCourse).orElse(null);
        if (course != null) {
            Set<Course> courseSet = new HashSet<>();
            courseSet.add(course);
            instructor.setCourses(courseSet);
            return instructorRepository.save(instructor);
        }
        return null; // or handle error
    }

    @Override
    public List<Instructor> findInstructorsByCourse(Long numCourse) {
        Course course = courseRepository.findById(numCourse).orElse(null);
        if (course != null) {
            return instructorRepository.findByCoursesContaining(course);
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteInstructor(Long numInstructor) {
        if (instructorRepository.existsById(numInstructor)) {
            instructorRepository.deleteById(numInstructor);
        } else {
            throw new RuntimeException("Instructor not found"); // or handle error accordingly
        }
    }

    @Override
    public Instructor assignCourseToInstructor(Long numInstructor, Long numCourse) {
        Instructor instructor = retrieveInstructor(numInstructor);
        Course course = courseRepository.findById(numCourse).orElse(null);
        if (instructor != null && course != null) {
            Set<Course> courses = instructor.getCourses() != null ? instructor.getCourses() : new HashSet<>();
            courses.add(course);
            instructor.setCourses(courses);
            return instructorRepository.save(instructor);
        }
        return null; // or handle the error appropriately
    }

    public Instructor updateInstructorCourses(Long numInstructor, Set<Course> newCourses) {
        Instructor instructor = retrieveInstructor(numInstructor);
        if (instructor != null) {
            instructor.setCourses(newCourses);
            return instructorRepository.save(instructor);
        }
        return null; // or handle error
    }

    public Instructor assignInstructorsToMultipleCourses(Long numInstructor, Set<Long> courseIds) {
        Instructor instructor = retrieveInstructor(numInstructor);
        Set<Course> courses = new HashSet<>();
        for (Long courseId : courseIds) {
            Course course = courseRepository.findById(courseId).orElse(null);
            if (course != null) {
                courses.add(course);
            }
        }
        if (instructor != null) {
            instructor.setCourses(courses);
            return instructorRepository.save(instructor);
        }
        return null; // or throw an exception if not found
    }
}
