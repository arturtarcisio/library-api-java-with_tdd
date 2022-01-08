package io.github.libraryapi.api.resource;

import io.github.libraryapi.api.dto.BookDTO;
import io.github.libraryapi.model.entity.Book;
import io.github.libraryapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody BookDTO bookDTO) {
        Book entity = service.save(Book.builder()
                        .author(bookDTO.getAuthor())
                        .title(bookDTO.getTitle())
                        .isbn(bookDTO.getIsbn())
                        .build());

        return BookDTO.builder()
                .id(entity.getId())
                .author(entity.getAuthor())
                .title(entity.getTitle())
                .isbn(entity.getIsbn())
                .build();
    }

}
