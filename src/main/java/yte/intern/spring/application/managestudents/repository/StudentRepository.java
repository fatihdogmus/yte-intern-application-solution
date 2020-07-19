package yte.intern.spring.application.managestudents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.intern.spring.application.managestudents.entity.Student;

import javax.transaction.Transactional;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

	Optional<Student> findByStudentNumber(String studentNumber);

	@Transactional
	void deleteByStudentNumber(String studentNumber);
}
