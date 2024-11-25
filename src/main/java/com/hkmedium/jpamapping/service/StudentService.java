package com.hkmedium.jpamapping.service;

import com.hkmedium.jpamapping.entity.Course;
import com.hkmedium.jpamapping.entity.Student;
import com.hkmedium.jpamapping.repository.CourseRepository;
import com.hkmedium.jpamapping.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
        //return studentRepository.findAllWithCourses();
    }

    public Student getStudentById(int studentId) {
        return studentRepository.findById(studentId).get();
    }

    public List<Student> findByStudentNameContaining(String studentName){
        return studentRepository.findByStudentNameContaining(studentName);
    }

    @Transactional
    public String assignCourseToStudent(int studentId, int courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        // Update both sides of the relationship
        student.getCourses().add(course);
        course.getStudents().add(student);
        // Save only one side (Hibernate manages the join table automatically)
        studentRepository.save(student);
        return "Success";
    }

    @Transactional
    public String deleteCourseFromStudent(int studentId, int courseId) {
        // Fetch the student and handle if not found
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        // Fetch the course and handle if not found
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        // Remove the association on both sides if it exists
        if (student.getCourses().contains(course)) {
            student.getCourses().remove(course);
            course.getStudents().remove(student);
        }

        return "Success";
    }

    public Set<Course> getCoursesByStudentId(int studentId) {
        return studentRepository.findCoursesByStudentId(studentId);
    }

    public List<Student> findStudentsByCoursesCourseId(int courseId) {
        List<Student> studentList = studentRepository.findStudentsByCoursesCourseId(courseId);
        return studentList;
    }

    @Transactional
    public Student updateStudent(int id, Student updatedStudent) {
        // Find the student or throw an exception if not found
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
        if (updatedStudent.getStudentName() != null && !updatedStudent.getStudentName().isEmpty()) {
            student.setStudentName(updatedStudent.getStudentName());
        }
        return studentRepository.save(student);
    }

    @Transactional
    public String deleteStudent(int stdId) {
        Student student = studentRepository.findById(stdId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        // Ensure bidirectional relationship is updated
        for (Course course : student.getCourses()) {
            course.getStudents().remove(student);
        }
        student.getCourses().clear(); // Clear the student's side of the relationship
        studentRepository.delete(student); // Delete the student
        return "Success";
    }

    public String deleteAllStudents() {
        studentRepository.deleteAll();
        return "Success";
    }
}
