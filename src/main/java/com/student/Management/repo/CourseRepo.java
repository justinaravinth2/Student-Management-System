package com.student.Management.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.student.Management.entity.CourseVO;

@Repository
public interface CourseRepo extends JpaRepository<CourseVO, Long> {

	boolean existsByCourseNameIgnoreCase(String courseName);

	CourseVO findByCourseNameIgnoreCase(String courseName);

}
