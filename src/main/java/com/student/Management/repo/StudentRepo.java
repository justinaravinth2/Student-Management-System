package com.student.Management.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.student.Management.entity.CourseVO;
import com.student.Management.entity.StudentVO;

@Repository
public interface StudentRepo extends JpaRepository<StudentVO, Long> {





	Optional<StudentVO> findByStudentCodeIgnoreCase(String studentCode);


	boolean existsByStudentCodeIgnoreCase(String studentCode);


//	@Query("SELECT s FROM StudentVO s JOIN s.courses c WHERE c.id = ?1")
//	List<StudentVO> findStudentsByCourse(Long courseId);


	List<StudentVO> findByCourses(CourseVO courseVO);

}
