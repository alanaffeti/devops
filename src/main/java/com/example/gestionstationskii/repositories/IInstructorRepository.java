package com.example.gestionstationskii.repositories;

import com.example.gestionstationskii.entities.Course;
import com.example.gestionstationskii.entities.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IInstructorRepository extends JpaRepository<Instructor, Long> {
    List<Instructor> findByCoursesContaining(Course course);
}
