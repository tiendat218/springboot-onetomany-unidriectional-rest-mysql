package com.example.springbootonetomanyunidriectionalrestmysql.dto;

import com.example.springbootonetomanyunidriectionalrestmysql.jpa.Book;
import com.example.springbootonetomanyunidriectionalrestmysql.jpa.Library;
import org.springframework.stereotype.Component;

import java.util.List;
import org.mapstruct.Mapper;
@Component
@Mapper
public interface IMapper {

    BookDto toBookDto(Book book);
    List<BookDto> toBookDtos(List<Book> books);
    Book toBook(BookDto bookDto);
    List<LibraryDto> toLibraryDtos(List<Library> libraries);
    LibraryDto toLibraryDto(Library library);
    Library toLibrary(LibraryDto libraryDto);

}
