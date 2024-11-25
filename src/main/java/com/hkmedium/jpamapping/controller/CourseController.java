package com.hkmedium.jpamapping.controller;


import com.hkmedium.jpamapping.entity.Course;
import com.hkmedium.jpamapping.entity.Student;
import com.hkmedium.jpamapping.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@Tag(name = "Course Management API", description = "Endpoints for managing course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/courses")
    public ResponseEntity<?> createStudent(@RequestBody Course cour) {
        Course course = courseService.saveCourse(cour);
        return new ResponseEntity<>(course, HttpStatus.CREATED);
    }

    @PostMapping("/students/{studentId}/courses")
    public ResponseEntity<Course> addTagModified(@PathVariable int studentId,
                                              @RequestBody Course course) {
        Course savedCourse = courseService.addCourse(studentId, course);
        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    }

    @GetMapping("/courses")
    public ResponseEntity<?> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable("courseId") int courseId) {
        Course course = courseService.getCourseById(courseId);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @GetMapping("/course/courses/{courseId}/students")
    public ResponseEntity<?> getStudentsByCourseId(@PathVariable("courseId") int courseId) {
        Set<Student> students = courseService.getStudentsByCourseId(courseId);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/course/students/{studentId}/courses")
    public ResponseEntity<?> getCoursesByStudentId(@PathVariable("studentId") int studentId) {
        List<Course> coursesByStudentId = courseService.getCoursesByStudentsStudentId(studentId);
        return new ResponseEntity<>(coursesByStudentId, HttpStatus.OK);
    }

    @PutMapping("/courses/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable("courseId") int courseId,
                                          @RequestBody Course updatedCourse) {
        String mes = courseService.updateCourse(courseId, updatedCourse);
        return new ResponseEntity<>(mes, HttpStatus.OK);
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable("courseId") int courseId) {
        String mes = courseService.deleteCourse(courseId);
        return new ResponseEntity<>(mes, HttpStatus.OK);
    }

    @DeleteMapping("/courses")
    public String deleteAllStudents() {
        String message = courseService.deleteAllCourses();
        return message;
    }

}
