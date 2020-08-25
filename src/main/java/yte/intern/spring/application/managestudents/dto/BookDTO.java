package yte.intern.spring.application.managestudents.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
public class BookDTO {

	@JsonProperty("title")
	@Size(max = 255, message = "Title can be at most 255 characters!")
	public final String title;

	@JsonProperty("publishDate")
	@PastOrPresent(message = "Publish date can't be in the future!")
	public final LocalDate publishDate;

	@JsonProperty("pageCount")
	@Min(value = 100, message = "Page count can be minimum 100!")
	@Max(value = 1500, message = "Page count can be maximum 1500!")
	public final Long pageCount;

	public BookDTO(@JsonProperty("title") String title,
				   @JsonProperty("publishDate") LocalDate publishDate,
				   @JsonProperty("pageCount") Long pageCount) {
		this.title = title;
		this.publishDate = publishDate;
		this.pageCount = pageCount;
	}
}
