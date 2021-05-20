package com.example.springbootonetomanyunidriectionalrestmysql.api;

import com.example.springbootonetomanyunidriectionalrestmysql.dto.IMapper;
import com.example.springbootonetomanyunidriectionalrestmysql.dto.LibraryDto;
import com.example.springbootonetomanyunidriectionalrestmysql.jpa.Library;
import com.example.springbootonetomanyunidriectionalrestmysql.service.ILibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tests")
public class Library_Controller {

    private ILibraryService libraryService;

    private IMapper mapper;

    @GetMapping
    public ResponseEntity<List<LibraryDto>> getAll() {
        return ResponseEntity.ok(mapper.toLibraryDtos(libraryService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryDto> getLibraryById(@Valid @PathVariable Integer id) {
        if (!libraryService.findById(id).isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(mapper.toLibraryDto(libraryService.findById(id).get()));
    }

    @PostMapping
    public ResponseEntity<LibraryDto> createLibrary(@Valid @RequestBody LibraryDto libraryDto) {
        libraryService.save(mapper.toLibrary(libraryDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<LibraryDto> updateLibrary(@Valid @PathVariable Integer id, @RequestBody LibraryDto libraryDto) {
        Library library = mapper.toLibrary(libraryDto);
        Optional<Library> libraryOptional = libraryService.findById(id);
        if (!libraryOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        libraryService.save(library);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(libraryDto);
    }
}
