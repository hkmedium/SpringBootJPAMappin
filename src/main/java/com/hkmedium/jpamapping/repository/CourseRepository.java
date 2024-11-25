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
public interface CourseRepository extends JpaRepository<Course, Integer> {
    // Custom query to get the students for a specific course
    @Query("SELECT c.students FROM Course c WHERE c.courseId = :courseId")
    Set<Student> findStudentsByCourseId(@Param("courseId") int courseId);

    List<Course> findCoursesByStudentsStudentId(int studentId);
}
