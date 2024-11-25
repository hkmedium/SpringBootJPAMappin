package com.hkmedium.jpamapping.repository;

import com.hkmedium.jpamapping.entity.Course;
import com.hkmedium.jpamapping.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findAllByStudentId(int studentId);

    @Query("SELECT s.courses FROM Student s WHERE s.studentId = :studentId")
    Set<Course> findCoursesByStudentId(@Param("studentId") int studentId);

    @Query("SELECT s FROM Student s JOIN FETCH s.courses")
    List<Student> findAllWithCourses();

    List<Student> findStudentsByCoursesCourseId(int courseId);

    List<Student> findByStudentNameContaining(String studentName);
}
