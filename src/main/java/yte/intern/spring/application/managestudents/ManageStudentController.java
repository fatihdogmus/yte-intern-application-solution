package yte.intern.spring.application.managestudents;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yte.intern.spring.application.managestudents.dto.BookDTO;
import yte.intern.spring.application.managestudents.dto.StudentDTO;
import yte.intern.spring.application.managestudents.entity.Book;
import yte.intern.spring.application.managestudents.entity.Student;
import yte.intern.spring.application.managestudents.mapper.BookMapper;
import yte.intern.spring.application.managestudents.mapper.StudentMapper;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/students")
public class ManageStudentController {

	private final ManageStudentService manageStudentService;
	private final StudentMapper studentMapper;
	private final BookMapper bookMapper;

	@GetMapping
	public List<StudentDTO> listAllStudents() {
		List<Student> student = manageStudentService.listAllStudents();
		return studentMapper.mapToDto(student);
	}

	@GetMapping("/{studentNumber}")
	public StudentDTO getStudentByStudentNumber(@PathVariable @Size(max = 7, min = 7) String studentNumber) {
		Student student = manageStudentService.getStudentByStudentNumber(studentNumber);
		return studentMapper.mapToDto(student);
	}

	@PostMapping
	public StudentDTO addStudent(@Valid @RequestBody StudentDTO studentDTO) {
		Student student = studentMapper.mapToEntity(studentDTO);
		Student addedStudent = manageStudentService.addStudent(student);
		return studentMapper.mapToDto(addedStudent);
	}

	@PutMapping("/{studentNumber}")
	public StudentDTO updateStudent(@PathVariable @Size(max = 7, min = 7) String studentNumber, @Valid @RequestBody StudentDTO studentDTO) {
		Student student = studentMapper.mapToEntity(studentDTO);
		Student updatedStudent = manageStudentService.updateStudent(studentNumber, student);
		return studentMapper.mapToDto(updatedStudent);
	}

	@DeleteMapping("/{studentNumber}")
	public void deleteStudent(@PathVariable @Size(max = 7, min = 7) String studentNumber) {
		manageStudentService.deleteStudent(studentNumber);
	}

	@GetMapping("/{studentNumber}/books")
	public List<BookDTO> getStudentsBooks(@PathVariable String studentNumber) {
		Set<Book> studentsBooks = manageStudentService.getStudentsBooks(studentNumber);
		return bookMapper.mapToDto(new ArrayList<>(studentsBooks));
	}

	@PostMapping("/{studentNumber}/books")
	public BookDTO addBookToStudent(@PathVariable String studentNumber, @RequestBody @Valid BookDTO bookDTO) {
		Book addedBook = manageStudentService.addBookToStudent(studentNumber, bookMapper.mapToEntity(bookDTO));
		return bookMapper.mapToDto(addedBook);
	}

	@DeleteMapping("/{studentNumber}/books/{bookTitle}")
	public void addBookToStudent(@PathVariable String studentNumber, @PathVariable String bookTitle) {
		manageStudentService.deleteBook(studentNumber, bookTitle);
	}

}
