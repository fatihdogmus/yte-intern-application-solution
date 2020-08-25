package yte.intern.spring.application.managestudents;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.intern.spring.application.common.dto.MessageResponse;
import yte.intern.spring.application.managestudents.entity.Book;
import yte.intern.spring.application.managestudents.entity.Student;
import yte.intern.spring.application.managestudents.repository.StudentRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static yte.intern.spring.application.common.enums.MessageType.ERROR;
import static yte.intern.spring.application.common.enums.MessageType.SUCCESS;

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

	public MessageResponse addStudent(Student student) {
		student.setBooks(Set.of(new Book("Introduction to Algorithms", LocalDate.parse("1989-01-01"), 1312L)));
		studentRepository.save(student);
		return new MessageResponse("Student has been added successfully!", SUCCESS);
	}

	/*
		Derste update ile ilgili bir problem olmuştu. Bunun sebebi, version kullandığımız için ID'sini setlediğimiz
		controller'dan gelen entity'i yeni bir entity olarak algılıyordu. Onu düzelttikten sonra da book'lar olmadığı için
		hata hattı. Bu sebeple bir entity'i güncellemenin en iyi yolunun doğrudan veri tabanından getirdiğimiz entity'nin
		field'larını controller'dan gelen entity ile güncellemek olduğunu fark ettim. Orjinal çözüm kadar temiz değil, ama
		çalışıyor. Öbür türlü tüm relation'ları tek tek zaten map'lememiz gerekiyordu. Onun yerine doğrudan veri tabanından
		gelen entity'i güncellemenin daha mantıklı olduğuna karar verdim.
	 */
	@Transactional
	public MessageResponse updateStudent(String studentNumber, Student student) {
		Optional<Student> studentOptional = studentRepository.findByStudentNumber(studentNumber);
		if (studentOptional.isPresent()) {
			Student studentFromDB = studentOptional.get();
			updateStudentFromDB(student, studentFromDB);
			studentRepository.save(studentFromDB);
			return new MessageResponse(String.format("Student with student number %s has been updated successfully!", studentNumber), SUCCESS);
		} else {
			return new MessageResponse(String.format("Student with student number %s can't be found!", studentNumber), ERROR);
		}

	}

	private void updateStudentFromDB(Student student, Student studentFromDB) {
		studentFromDB.setEmail(student.getEmail());
		studentFromDB.setName(student.getName());
		studentFromDB.setStudentNumber(student.getStudentNumber());
		studentFromDB.setTcKimlikNo(student.getTcKimlikNo());
	}

	public MessageResponse deleteStudent(String studentNumber) {
		if (studentRepository.existsByStudentNumber(studentNumber)) {
			studentRepository.deleteByStudentNumber(studentNumber);
			return new MessageResponse(String.format("Student with student number %s has been been deleted successfully!", studentNumber), SUCCESS);
		} else {
			return new MessageResponse(String.format("Student with student number %s can't be found!", studentNumber), ERROR);
		}
	}

	/**
	 * Burada bussiness rule'larımızı işletiyoruz. Eğer öğrencinin 5 kitabı varsa veya eklenmeye çalışılan kitap
	 * zaten öğrencinin elinde varsa bir exception fırlatıyoruz(genellikle exception fırlatmak yerine özel bir error nesnesi
	 * dönmek daha mantıklı, fakat aşırı karmaşık olacağı için şimdilik sadece exception fırlatmayı tercih ettim. Fakat
	 * bazıları bussiness rule'ların oluşan hatalar için exception fırmatlamanın doğru olmadığını söylüyorlar, detaylı bir konu)
	 * Burda service layer'ında bussiness rule'ların kontrolünü yapmak yerine, bu kontrolleri doğrudan entity'nin üzerine attım.
	 * Bu da biraz daha advanced seviyede büyük uygulamalarda görebileceğiniz bir pratik. Mümkün olduğunca bussiness rule execution'larını
	 * entity'lerin üzerine atmanın iyi olduğunu söylüyorlar. O yüzden burda if(student.getBooks().size == 5) gibi bir kod yerine doğrudan
	 * student'a 5 kitabı olup olmadığını soruyorum.
	 */
	public MessageResponse addBookToStudent(String studentNumber, Book book) {
		Optional<Student> studentOptional = studentRepository.findByStudentNumber(studentNumber);
		if (studentOptional.isPresent()) {
			Student student = studentOptional.get();
			Set<Book> books = student.getBooks();

			if (student.hasFiveBooks()) {
				return new MessageResponse(String.format("Student with student number %s has already 5 books!", studentNumber), ERROR);
			} else if (student.hasBook(book.getTitle())) {
				return new MessageResponse(String.format("Student with student number %s has already book %s", studentNumber, book.getTitle()), ERROR);
			}

			books.add(book);
			return new MessageResponse(String.format("Book %s has been added to student successfylly!", book.getTitle()), ERROR);

		} else {
			return new MessageResponse(String.format("Student with student number %s can't be found!", studentNumber), ERROR);
		}
	}

	/*
		Burada tuhaf şeyler oluyor arkadaşlar. Hibernate, bir sete, doğrudan assigning yapmayı sevmiyor. Orjinal olarak
		burda student.setBooks(filteredBooks) yapıyordum, fakat set referansı değiştiği anda hibernate hata atıyor. Çözüm
		olarak seti temizleyip, filtre edilmiş seti student'ımızın book'larına ekleyip kaydediyoruz. Bu sayede hibernate hata
		atmıyor, bizde istediğimiz elemanı setten çıkarmış oluyoruz.
	 */
	public MessageResponse deleteBook(String studentNumber, String bookTitle) {
		Optional<Student> studentOptional = studentRepository.findByStudentNumber(studentNumber);
		if (studentOptional.isPresent()) {
			Student student = studentOptional.get();
			if(!student.hasBook(bookTitle)) {
				return new MessageResponse(String.format("Student with student number %s doesn't have book %s!", studentNumber, bookTitle),ERROR);
			}
			removeBookFromStudent(bookTitle, student);
			studentRepository.save(student);
			return new MessageResponse(String.format("Book %s has been deleted from student successfylly!", bookTitle), SUCCESS);
		}
		return new MessageResponse(String.format("Student with student number %s can't be found!", studentNumber), ERROR);
	}


	private void removeBookFromStudent(String bookTitle, Student student) {
		Set<Book> filteredBooks = student.getBooks()
				.stream()
				.filter(it -> !it.getTitle().equals(bookTitle))
				.collect(toSet());

		student.getBooks().clear();
		student.getBooks().addAll(filteredBooks);
	}
}
