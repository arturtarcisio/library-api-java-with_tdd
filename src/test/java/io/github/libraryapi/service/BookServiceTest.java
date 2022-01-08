package io.github.libraryapi.service;

import io.github.libraryapi.model.entity.Book;
import io.github.libraryapi.repository.BookRepository;
import io.github.libraryapi.service.impl.BookServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    BookService service;

    @MockBean
    private BookRepository repository;

    @BeforeEach
    public void setUp() {
        service = new BookServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve salvar um livro.")
    public void saveBookTest(){
        // Cenário
        Book book = Book.builder()
                .isbn("123")
                .author("Fulano")
                .title("As aventuras")
                .build();

        Mockito.when(repository.save(book))
                .thenReturn(Book.builder()
                        .id(1L)
                        .author("Fulano")
                        .title("As aventuras")
                        .isbn("123")
                        .build());

        // Execução
        Book savedBook = service.save(book);

        // Assertivas / Verificação
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getIsbn()).isEqualTo("123");
        assertThat(savedBook.getAuthor()).isEqualTo("Fulano");
        assertThat(savedBook.getTitle()).isEqualTo("As aventuras");
    }
}
