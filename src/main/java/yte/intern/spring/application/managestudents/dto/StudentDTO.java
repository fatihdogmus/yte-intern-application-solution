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

	/** StudentDTO ve BookDTO derste yaptığımızdan biraz farklı. Bunu sebebi DTO objelerinde immutability'yi sağlamaya
	 * çalışmamdan kaynaklı. Farkettiyseniz tüm field'lar public ve final, ve sadece @Getter var. Bunun sebebi bir
	 * class eğer immutable olursa, içeriğindeki state değişiminden kaynaklı pek çok hatanın önüne geçmiş oluruz.
	 * Immutability ile ilgili pek çok kaynak var, giriş olarak bu makaleyi okuyabilirsiniz:
	 * <l>https://www.leadingagile.com/2018/03/immutability-in-java/</l>
	 * @JsonProperty ise gelen JSON objesinin, bizim DTO objemize field ile değil, constructor ile initialize etmesini
	 * sağlar. Normalde bildiriğiniz gibi jackson, JSON'u setter methodları ile map'liyor, fakat burda şu anda setter
	 * olmadığı için doğrudan constructor ile yapmamız gerekiyor. Bunun için de aşağıdaki @JsonCreator annotation'ını
	 * consturctor üzerine koyarak jackson'a mapping işlemini constructor ile yapmasını söylüyoruz. JSON'daki hangi field'ın
	 * hangi constructor parametresine karşılık geldiğini de @JsonProperty ile söylüyoruz.
	 * @Builder ise yine lombok tarafında sağlanan bir annotation ve builder pattern'ının kullanımını sağlıyor.
	 * Builder pattern'ı için daha detaylı bilgi için bkn: <l>https://dzone.com/articles/design-patterns-the-builder-pattern</l>
	 * Bu annotation'ını da MapStruct için kullanıyoruz. Normalde MapStruct da bildiğiniz gibi setter'lar kullanıyor, fakat
	 * setter olmayınca builder pattern'ını kullanarak iki yer arasında mapping yapıyor.
	 *
	 */
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
