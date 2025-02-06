package com.student.Management.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.student.Management.entity.StudentAddressVO;
import com.student.Management.entity.StudentVO;

@Repository
public interface StudentAddressRepo extends JpaRepository<StudentAddressVO, Long>{

	List<StudentAddressVO> findByStudent(StudentVO student);

}
