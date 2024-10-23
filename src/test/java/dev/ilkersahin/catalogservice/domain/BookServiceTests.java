package dev.ilkersahin.catalogservice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookService bookService;

    @Test
    void whenBookToCreateAlreadyExists_ThenThrows() {
        var bookIsbn = "1234561232";
        var bookToCreate = new Book(bookIsbn, "Title", "Author", 9.90);
        BDDMockito.when(bookRepository.existsByIsbn(bookIsbn)).thenReturn(true);
        Assertions.assertThatThrownBy(() -> bookService.addBookToCatalog(bookToCreate))
                .isInstanceOf(BookAlreadyExistsException.class)
                .hasMessage("Book with ISBN [" + bookIsbn + "] already exists.");
    }

    @Test
    void whenBookToReadDoesNotExist_ThenThrows() {
        var bookIsbn = "1234561232";
        BDDMockito.when(bookRepository.findByIsbn(bookIsbn)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> bookService.viewBookDetails(bookIsbn))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("Book with ISBN [" + bookIsbn + "] was not found.");
    }
}
