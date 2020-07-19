package yte.intern.spring.application.managestudents.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import yte.intern.spring.application.managestudents.validation.TcKimlikNo;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Builder
public class StudentDTO {

	@Size(max = 255, message = "Name can't be longer than 255!")
	public final String name;

	@Size(max = 255, message = "Surname can't be longer than 255!")
	public final String surname;

	@Email(message = "Please enter a valid e-mail address!")
	@Size(max = 255, message = "E-mail can't be longer than 255!")
	public final String email;

	@Size(min = 11, max = 11, message = "TC Kimlik no must be 11 characters long!")
	@TcKimlikNo(message = "TC Kimlik No must be valid!")
	public final String tcKimlikNo;

	@Size(min = 7, max = 7, message = "Student number must be 7 characters long!")
	public final String studentNumber;

	@JsonCreator
	public StudentDTO(@JsonProperty("name") String name,
	                  @JsonProperty("surname") String surname,
	                  @JsonProperty("email") String email,
	                  @JsonProperty("tcKimlikNo") String tcKimlikNo,
	                  @JsonProperty("studentNumber") String studentNumber) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.tcKimlikNo = tcKimlikNo;
		this.studentNumber = studentNumber;
	}
}
