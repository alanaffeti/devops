package com.example.gestionstationskii.controllers;

import com.example.gestionstationskii.services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.gestionstationskii.entities.*;

import java.util.List;

@Tag(name = "\uD83D\uDC69\u200D\uD83C\uDFEB Instructor Management")
@RestController
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorRestController {

    private final IInstructorServices instructorServices;

    @Operation(description = "Add Instructor")
    @PostMapping("/add")
    public Instructor addInstructor(@RequestBody Instructor instructor) {
        return instructorServices.addInstructor(instructor);
    }

    @Operation(description = "Add Instructor and Assign To Course")
    @PutMapping("/addAndAssignToCourse/{numCourse}")
    public Instructor addAndAssignToInstructor(@RequestBody Instructor instructor, @PathVariable("numCourse") Long numCourse) {
        return instructorServices.addInstructorAndAssignToCourse(instructor, numCourse);
    }

    @Operation(description = "Retrieve all Instructors")
    @GetMapping("/all")
    public List<Instructor> getAllInstructors() {
        return instructorServices.retrieveAllInstructors();
    }

    @Operation(description = "Update Instructor")
    @PutMapping("/update")
    public Instructor updateInstructor(@RequestBody Instructor instructor) {
        return instructorServices.updateInstructor(instructor);
    }

    @Operation(description = "Retrieve Instructor by Id")
    @GetMapping("/get/{id-instructor}")
    public Instructor getById(@PathVariable("id-instructor") Long numInstructor) {
        return instructorServices.retrieveInstructor(numInstructor);
    }

    @Operation(description = "Retrieve Instructors by Course")
    @GetMapping("/byCourse/{numCourse}")
    public List<Instructor> getInstructorsByCourse(@PathVariable Long numCourse) {
        return instructorServices.findInstructorsByCourse(numCourse);
    }

    @Operation(description = "Delete Instructor by Id")
    @DeleteMapping("/delete/{numInstructor}")
    public void deleteInstructor(@PathVariable Long numInstructor) {
        instructorServices.deleteInstructor(numInstructor);
    }

    @Operation(description = "Assign Course to Instructor")
    @PostMapping("/assignCourse/{numInstructor}/{numCourse}")
    public Instructor assignCourseToInstructor(@PathVariable Long numInstructor, @PathVariable Long numCourse) {
        return instructorServices.assignCourseToInstructor(numInstructor, numCourse);
    }
}
