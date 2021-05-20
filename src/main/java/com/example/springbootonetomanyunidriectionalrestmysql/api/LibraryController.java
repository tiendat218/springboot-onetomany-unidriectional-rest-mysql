package com.example.springbootonetomanyunidriectionalrestmysql.api;

import com.example.springbootonetomanyunidriectionalrestmysql.jpa.Library;
import com.example.springbootonetomanyunidriectionalrestmysql.repository.IBookRepository;
import com.example.springbootonetomanyunidriectionalrestmysql.repository.ILibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/libraries")
public class LibraryController {
    private final ILibraryRepository libraryRepository;
    private final IBookRepository bookRepository;

    @Autowired
    public LibraryController(ILibraryRepository libraryRepository, IBookRepository bookRepository) {
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
    }

    @PostMapping
    public ResponseEntity<Library> createLibrary(@Valid @RequestBody Library library){
        Library librarySaved = libraryRepository.save(library);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(librarySaved.getId())
                .toUri();
        return ResponseEntity.created(location).body(librarySaved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Library> deleteLibrary(@PathVariable Integer id){
        //Check xem ton tai library can xoa khong
        Optional<Library> optionalLibrary = libraryRepository.findById(id);
        if (!optionalLibrary.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.noContent().build();
    }

    @Transactional
    public void deleteLibraryCustomTransactional(Library library){
        bookRepository.deleteLibraryId(library.getId());
        libraryRepository.delete(library);
    }

    @GetMapping("/")
    public ResponseEntity<Page<Library>> getAll(Pageable pageable){
        return ResponseEntity.ok(libraryRepository.findAll(pageable));
    }
}
