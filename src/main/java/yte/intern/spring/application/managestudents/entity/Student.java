package yte.intern.spring.application.managestudents.entity;

import lombok.Getter;
import lombok.Setter;
import yte.intern.spring.application.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "idgen", sequenceName = "STUDENT_SEQ")
public class Student extends BaseEntity {

	@Column(name = "NAME")
	private String name;

	@Column(name = "SURNAME")
	private String surname;

	@Column(name = "EMAIL", unique = true)
	private String email;

	@Column(name = "TC_KIMLIK_NO", unique = true)
	private String tcKimlikNo;

	@Column(name = "STUDENT_NUMBER", unique = true)
	private String studentNumber;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "STUDENT_ID")
	private Set<Book> books;

	public boolean hasFiveBooks() {
		return books.size() == 5;
	}

	public boolean hasBook(Book book) {
		return books.stream().anyMatch(it -> it.getTitle().equals(book.getTitle()));
	}
}
