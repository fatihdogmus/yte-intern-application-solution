package yte.intern.spring.application.managestudents.mapper;

import org.mapstruct.Mapper;
import yte.intern.spring.application.managestudents.dto.BookDTO;
import yte.intern.spring.application.managestudents.entity.Book;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

	BookDTO mapToDto(Book book);

	Book mapToEntity(BookDTO bookDTO);

	List<BookDTO> mapToDto(List<Book> bookList);

	List<Book> mapToEntity(List<BookDTO> bookDTOList);
}
