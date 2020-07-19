package yte.intern.spring.application.managestudents.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import yte.intern.spring.application.managestudents.dto.StudentDTO;
import yte.intern.spring.application.managestudents.entity.Student;

import java.util.List;

@Mapper(componentModel = "spring",injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface StudentMapper {

	StudentDTO mapToDto(Student student);

	Student mapToEntity(StudentDTO studentDTO);

	List<StudentDTO> mapToDto(List<Student> studentList);

	List<Student> mapToEntity(List<StudentDTO> studentDTOList);
}
