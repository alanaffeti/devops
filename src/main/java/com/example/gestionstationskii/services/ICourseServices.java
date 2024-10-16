package com.example.gestionstationskii.services;


import com.example.gestionstationskii.entities.Course;
import com.example.gestionstationskii.entities.Support;
import com.example.gestionstationskii.entities.TypeCourse;

import java.util.List;

public interface ICourseServices {

    List<Course> retrieveAllCourses();

    Course  addCourse(Course  course);

    Course updateCourse(Course course);

    Course retrieveCourse(Long numCourse);

    List<Course> getCoursesByLevelAndRating(int level, float minRating);
    List<Course> getAdvancedFilteredCourses(int level, TypeCourse typeCourse, Support support, float minRating);

}
