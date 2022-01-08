package io.github.libraryapi.api.resource;

import io.github.libraryapi.api.dto.BookDTO;
import io.github.libraryapi.model.entity.Book;
import io.github.libraryapi.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody BookDTO bookDTO) {
        Book entity = service.save(modelMapper.map(bookDTO, Book.class));

        return modelMapper.map(entity, BookDTO.class);
    }

}
