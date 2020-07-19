package yte.intern.spring.application.managestudents.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yte.intern.spring.application.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "idgen", sequenceName = "BOOK_SEQ")
@AllArgsConstructor
@NoArgsConstructor
public class Book extends BaseEntity {

	@Column(name = "TITLE")
	private String title;

	@Column(name = "PUBLISH_DATE")
	private LocalDate publishDate;

	@Column(name = "PAGE_COUNT")
	private Long pageCount;
}
