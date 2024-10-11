package com.example.gestionstationskii.services;

import com.example.gestionstationskii.entities.Instructor;

import java.util.List;

public interface IInstructorServices {

    Instructor addInstructor(Instructor instructor);

    List<Instructor> retrieveAllInstructors();

    Instructor updateInstructor(Instructor instructor);

    Instructor retrieveInstructor(Long numInstructor);

    Instructor addInstructorAndAssignToCourse(Instructor instructor, Long numCourse);

    List<Instructor> findInstructorsByCourse(Long numCourse);

    void deleteInstructor(Long numInstructor); // New method for deleting an instructor

    Instructor assignCourseToInstructor(Long numInstructor, Long numCourse); // New method for assigning a course
}
