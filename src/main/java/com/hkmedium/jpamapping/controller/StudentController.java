package com.hkmedium.jpamapping.controller;

import com.hkmedium.jpamapping.entity.Course;
import com.hkmedium.jpamapping.entity.Student;
import com.hkmedium.jpamapping.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@Tag(name = "Student Management API", description = "Endpoints for managing students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/students")
    public ResponseEntity<?> createStudent(@RequestBody Student stud) {
        Student student1 = studentService.saveStudent(stud);
        return new ResponseEntity<>(student1, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all students", description = "Retrieve a list of all students")
    @GetMapping("/students") //http://localhost:8087/api/students?studentName=IT
    public ResponseEntity<List<Student>> getAllStudents(@RequestParam(required = false) String studentName) {
        List<Student> students = new ArrayList<Student>();
        if (studentName == null)
            studentService.getAllStudents().forEach(students::add);
        else
            studentService.findByStudentNameContaining(studentName).forEach(students::add);

        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/all-students")
    public ResponseEntity<?> getAllStudents() {
        List<Student> allStudent = studentService.getAllStudents();
        return new ResponseEntity<>(allStudent, HttpStatus.OK);
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<?> getAllStudents(@PathVariable("studentId") int studentId) {
        Student student = studentService.getStudentById(studentId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PutMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<?> assignCourseToStudent(@PathVariable("studentId") int studentId,
                                                   @PathVariable("courseId") int courseId) {
        String message = studentService.assignCourseToStudent(studentId, courseId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<?> deleteCourseFromStudent(@PathVariable("studentId") int studentId,
                                                     @PathVariable("courseId") int courseId) {
        String message = studentService.deleteCourseFromStudent(studentId, courseId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/students/{studentId}")
    public Student updateStudent(@PathVariable("studentId") int studentId,
                                 @RequestBody Student updatedStudent) {
        return studentService.updateStudent(studentId, updatedStudent);
    }

    @GetMapping("/students/{studentId}/courses")
    public ResponseEntity<?> getCoursesByStudentId(@PathVariable("studentId") int studentId) {
        Set<Course> coursesByStudentId = studentService.getCoursesByStudentId(studentId);
        return new ResponseEntity<>(coursesByStudentId, HttpStatus.OK);
    }

    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<?> findStudentsByCoursesCourseId(@PathVariable("courseId") int courseId) {
        List<Student> studentList = studentService.findStudentsByCoursesCourseId(courseId);
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    @DeleteMapping("/students/{studentId}")
    public String deleteStudent(@PathVariable("studentId") int studentId) {
        String message = studentService.deleteStudent(studentId);
        return message;
    }

    //@DeleteMapping("/students")
    public String deleteAllStudents() {
        String message = studentService.deleteAllStudents();
        return message;
    }

}
