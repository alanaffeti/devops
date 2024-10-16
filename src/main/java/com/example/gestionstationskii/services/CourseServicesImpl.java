package com.example.gestionstationskii.services;

import com.example.gestionstationskii.entities.Course;
import com.example.gestionstationskii.entities.Support;
import com.example.gestionstationskii.entities.TypeCourse;
import com.example.gestionstationskii.repositories.ICourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CourseServicesImpl implements  ICourseServices{

    private ICourseRepository courseRepository;

    @Override
    public List<Course> retrieveAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course retrieveCourse(Long numCourse) {
        return courseRepository.findById(numCourse).orElse(null);
    }

    @Override
    public List<Course> getCoursesByLevelAndRating(int level, float minRating) {
        List<Course> allCourses = courseRepository.findAll(); // Fetch all courses
        return allCourses.stream()
                .filter(course -> course.getLevel() == level
                        && course.getRating() >= minRating) // Filter by level and rating
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> getAdvancedFilteredCourses(int level, TypeCourse typeCourse, Support support, float minRating) {
        return courseRepository.findAll().stream()
                .filter(course -> course.getLevel() == level)
                .filter(course -> course.getTypeCourse().equals(typeCourse))
                .filter(course -> course.getSupport().equals(support))
                .filter(course -> course.getRating() >= minRating)
                .collect(Collectors.toList());
    }


}
