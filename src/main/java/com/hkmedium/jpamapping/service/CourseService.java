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
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Transactional
    public Course addCourse(int studentId, Course targetCourse) {
        // Fetch the student
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found!"));
        // Check if the course already exists
        Course course = courseRepository.findById(targetCourse.getCourseId())
                .orElse(targetCourse); // Use the provided course if not found

//        Course course;
//        // Check if the course already exists
//        Optional<Course> existingCourse = courseRepository.findById(targetCourse.getCourseId());
//        if (existingCourse.isPresent()) {
//            course = existingCourse.get();
//        } else {
//            // Save the new course to assign it an ID
//            course = courseRepository.save(targetCourse);
//        }

        // Maintain bidirectional relationship
        student.getCourses().add(course);
        course.getStudents().add(student);

        // Save only the student (cascading ensures the course is saved if necessary)
        studentRepository.save(student);

        return course;
    }


    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(int Id) {
        return courseRepository.findById(Id).get();
    }

    public Set<Student> getStudentsByCourseId(int courseId) {
        return courseRepository.findStudentsByCourseId(courseId);
    }

    public List<Course> getCoursesByStudentsStudentId(int studentId) {
        return courseRepository.findCoursesByStudentsStudentId(studentId);
    }

    @Transactional
    public String updateCourse(int courseId, Course updatedCourse) {
        if (courseRepository.findById(courseId).isPresent()) {
            Course course = courseRepository.findById(courseId).get();
            course.setCourseName(updatedCourse.getCourseName());
            course.setCourseDescription(updatedCourse.getCourseDescription());
            courseRepository.save(course);
            return "Success";
        }
        return "Fail";
    }

    @Transactional
    public String deleteCourse(int courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Clear associations in a bidirectional manner
        for (Student student : course.getStudents()) {
            student.getCourses().remove(course);
        }
        course.getStudents().clear();

        // Now delete the course
        courseRepository.delete(course);
        return "Success";
    }

    @Transactional
    public String deleteAllCourses() {
        courseRepository.deleteAll();
        return "Success";
    }

}
