package io.github.libraryapi.service.impl;

import io.github.libraryapi.model.entity.Book;
import io.github.libraryapi.repository.BookRepository;
import io.github.libraryapi.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book book) {
        return repository.save(book);
    }
}
