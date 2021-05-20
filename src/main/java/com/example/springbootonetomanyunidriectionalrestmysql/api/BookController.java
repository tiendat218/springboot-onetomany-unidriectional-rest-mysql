package com.example.springbootonetomanyunidriectionalrestmysql.api;

import com.example.springbootonetomanyunidriectionalrestmysql.jpa.Book;
import com.example.springbootonetomanyunidriectionalrestmysql.jpa.Library;
import com.example.springbootonetomanyunidriectionalrestmysql.repository.IBookRepository;
import com.example.springbootonetomanyunidriectionalrestmysql.repository.ILibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final IBookRepository bookRepository;
    private final ILibraryRepository libraryRepository;

    @Autowired
    public BookController(IBookRepository bookRepository, ILibraryRepository libraryRepository) {
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody @Valid Book book){
        Optional<Library> optionalLibrary= libraryRepository.findById(book.getLibrary().getId());
        if (!optionalLibrary.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        book.setLibrary(optionalLibrary.get());
        Book bookSaved = bookRepository.save(book);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(bookSaved.getId()).toUri();

        return ResponseEntity.created(location).body(bookSaved);
    }
    @GetMapping("/")
    public ResponseEntity<Page<Book>> getAllBook(Pageable pageable){
        return ResponseEntity.ok(bookRepository.findAll(pageable));
    }
}
