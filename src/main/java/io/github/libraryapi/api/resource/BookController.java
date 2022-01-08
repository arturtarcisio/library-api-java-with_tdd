package io.github.libraryapi.api.resource;

import io.github.libraryapi.api.dto.BookDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody BookDTO bookDTO) {
        return BookDTO.builder()
                .id(1L)
                .title("Meu livro")
                .author("Autor")
                .isbn("1212121")
                .build();
    }

}
