package io.github.libraryapi.api.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.libraryapi.api.dto.BookDTO;
import io.github.libraryapi.exceptions.BusinessException;
import io.github.libraryapi.model.entity.Book;
import io.github.libraryapi.service.BookService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

    static String BOOK_API_URL = "/api/books";

    @Autowired
    private MockMvc mvc;

    @MockBean
    BookService service;

    @Test
    @DisplayName("Deve criar um livro com sucesso.")
    public void createBookTest() throws Exception{

        BookDTO dto = createNewBook();

        Book savedBook = Book.builder()
                .id(10L)
                .author("Artur")
                .title("As aventuras")
                .isbn("001")
                .build();
        BDDMockito.given(service.save(Mockito.any(Book.class))).willReturn(savedBook);

        MockHttpServletRequestBuilder request = getMockHttpServletRequestBuilder(dto);

        mvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(10L))
                .andExpect(jsonPath("title").value(dto.getTitle()))
                .andExpect(jsonPath("author").value(dto.getAuthor()))
                .andExpect(jsonPath("isbn").value(dto.getIsbn()));
    }

    @Test
    @DisplayName("Deve lançar erro de validação quando não houver dados suficientes para criação do livro")
    public void createInvalidBookTest() throws Exception {

        MockHttpServletRequestBuilder request = getMockHttpServletRequestBuilder(new BookDTO());

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", Matchers.hasSize(3)));
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar cadastrar um livro com isbn ja existente")
    public void createBookWithDuplicatedIsbn() throws Exception{

        String mensagemErro = "ISBN já cadastrado!";

        BookDTO dto = createNewBook();

        MockHttpServletRequestBuilder request = getMockHttpServletRequestBuilder(dto);

        BDDMockito.given(service.save(Mockito.any(Book.class)))
                .willThrow(new BusinessException(mensagemErro));

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", Matchers.hasSize(1)))
                .andExpect(jsonPath("errors[0]").value(mensagemErro));

    }

    private MockHttpServletRequestBuilder getMockHttpServletRequestBuilder(BookDTO bookDTO) throws JsonProcessingException {

        String json = new ObjectMapper().writeValueAsString(bookDTO);

        return MockMvcRequestBuilders
                .post(BOOK_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
    }

    private MockHttpServletRequestBuilder getMockHttpServletRequestBuilder(Book book) throws JsonProcessingException {

        String json = new ObjectMapper().writeValueAsString(book);

        return MockMvcRequestBuilders
                .post(BOOK_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
    }

    private BookDTO createNewBook() {
        return BookDTO.builder()
                .author("Artur")
                .title("As aventuras")
                .isbn("001")
                .build();
    }
}
