package yte.intern.spring.application.managestudents;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yte.intern.spring.application.common.dto.MessageResponse;
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
/*
  @Validated annotation'ına girmeye vaktim olmadı. @Validated, spring'in sağladığı bir annotation ve @PathVariabl
  ve @RequestParam gibi obje tabanlı olmayan, doğrudan controller fonksiyonu parametresine alınan argümanları validate etmek için
  kullanılır. Fark ettiyseniz tüm @PathVariable'ların başında @Size gibi validation'lar koydum, bu path variable'lar ile
  de yanlış input yollanmasının önüne geçmek için. Bunları @Valid ile yapamıyoruz arkadaşlar, @Valid spring için sadece
  obje validation'larında çalışıyor, normal parametreleri validate etmiyor spring. Bunun için, class'ımızın başına @Validated
  yazarak doğrudan obje olmayan argümanlarımızın validation'larını da sağlıyoruz.
 */
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
	public MessageResponse addStudent(@Valid @RequestBody StudentDTO studentDTO) {
		Student student = studentMapper.mapToEntity(studentDTO);
		return manageStudentService.addStudent(student);
	}

	@PutMapping("/{studentNumber}")
	public MessageResponse updateStudent(@PathVariable @Size(max = 7, min = 7) String studentNumber, @Valid @RequestBody StudentDTO studentDTO) {
		Student student = studentMapper.mapToEntity(studentDTO);
		return manageStudentService.updateStudent(studentNumber, student);
	}

	@DeleteMapping("/{studentNumber}")
	public MessageResponse deleteStudent(@PathVariable @Size(max = 7, min = 7) String studentNumber) {
		return manageStudentService.deleteStudent(studentNumber);
	}

	@GetMapping("/{studentNumber}/books")
	public List<BookDTO> getStudentsBooks(@PathVariable @Size(max = 7, min = 7) String studentNumber) {
		Set<Book> studentsBooks = manageStudentService.getStudentsBooks(studentNumber);
		return bookMapper.mapToDto(new ArrayList<>(studentsBooks));
	}

	@PostMapping("/{studentNumber}/books")
	public MessageResponse addBookToStudent(@PathVariable @Size(max = 7, min = 7) String studentNumber, @RequestBody @Valid BookDTO bookDTO) {
		return manageStudentService.addBookToStudent(studentNumber, bookMapper.mapToEntity(bookDTO));
	}

	@DeleteMapping("/{studentNumber}/books/{bookTitle}")
	public MessageResponse addBookToStudent(@PathVariable @Size(max = 7, min = 7) String studentNumber, @PathVariable String bookTitle) {
		return manageStudentService.deleteBook(studentNumber, bookTitle);
	}
}
