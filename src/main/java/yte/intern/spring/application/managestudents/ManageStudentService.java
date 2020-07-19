package yte.intern.spring.application.managestudents;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.intern.spring.application.managestudents.entity.Book;
import yte.intern.spring.application.managestudents.entity.Student;
import yte.intern.spring.application.managestudents.repository.StudentRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class ManageStudentService {

	private final StudentRepository studentRepository;

	public List<Student> listAllStudents() {
		return studentRepository.findAll();
	}

	public Student getStudentByStudentNumber(String studentNumber) {
		return studentRepository.findByStudentNumber(studentNumber).orElseThrow(EntityNotFoundException::new);
	}

	public Set<Book> getStudentsBooks(String studentNumber) {
		return studentRepository.findByStudentNumber(studentNumber).map(Student::getBooks)
				.orElseThrow(EntityNotFoundException::new);
	}

	public Student addStudent(Student student) {
		student.setBooks(Set.of(new Book("Introduction to Algorithms", LocalDate.parse("1989-01-01"), 1312L)));
		return studentRepository.save(student);
	}

	public Student updateStudent(String studentNumber, Student student) {
		Optional<Student> studentFromDb = studentRepository.findByStudentNumber(studentNumber);
		if (studentFromDb.isPresent()) {
			return studentRepository.save(student);
		} else {
			throw new EntityNotFoundException();
		}

	}

	public void deleteStudent(String studentNumber) {
		studentRepository.deleteByStudentNumber(studentNumber);
	}

	public Book addBookToStudent(String studentNumber, Book book) {
		Optional<Student> studentOptional = studentRepository.findByStudentNumber(studentNumber);
		if (studentOptional.isPresent()) {
			Student student = studentOptional.get();
			Set<Book> books = student.getBooks();
			if (student.hasFiveBooks()) {
				throw new IllegalStateException();
			} else if (student.hasBook(book)) {
				throw new IllegalStateException();
			}
			books.add(book);
			Student savedStudent = studentRepository.save(student);
			return savedStudent
					.getBooks()
					.stream()
					.filter(it -> it.getTitle().equals(book.getTitle()))
					.collect(toList())
					.get(0);
		} else {
			throw new EntityNotFoundException();
		}
	}

	public void deleteBook(String studentNumber, String bookTitle) {
		Optional<Student> studentOptional = studentRepository.findByStudentNumber(studentNumber);
		if(studentOptional.isPresent()) {
			Student student = studentOptional.get();
			Set<Book> filteredBooks = student.getBooks().stream()
					.filter(it -> !it.getTitle().equals(bookTitle))
					.collect(toSet());
			student.setBooks(filteredBooks);
			studentRepository.save(student);
		}
	}
}
