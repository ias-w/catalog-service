// 3.4.2 Integration tests with @SpringBootTest

package dev.ilkersahin.catalogservice;

import dev.ilkersahin.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
// Loads a full Spring web application context and
// a Servlet container listening on a random port.
)
class CatalogServiceApplicationTests {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenGetRequestWithId_ThenBookReturned() {
        var bookIsbn = "1231231230";
        var bookToCreate = new Book(bookIsbn, "Title", "Author", 9.90);
        Book expectedBook = webTestClient
                .post()
                .uri("/books")
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(book -> assertThat(book).isNotNull())
                .returnResult().getResponseBody();

        webTestClient
                .get()
                .uri("/books/" + bookIsbn)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
                });
    }

    @Test
    void whenPostRequest_ThenBookCreated() {
        var expectedBook = new Book("1231231231", "Title", "Author", 9.90);

        webTestClient
                .post()
                .uri("/books")
                .bodyValue(expectedBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.isbn())
                            .isEqualTo(expectedBook.isbn());
                });
    }

    @Test
    void whenPutRequest_ThenBookUpdated() {
        var bookIsbn = "1231231232";
        var bookToCreate = new Book(bookIsbn, "Title", "Author", 9.90);
        Book createdBook = webTestClient
                .post()
                .uri("/books")
                .bodyValue(bookToCreate)
                .exchange()
                .expectBody(Book.class).value(book -> assertThat(book).isNotNull())
                .returnResult().getResponseBody();
        var bookToUpdate = new Book(bookIsbn, createdBook.title(), createdBook.author(), 19.90);
        webTestClient
                .put()
                .uri("/books/" + bookIsbn)
                .bodyValue(bookToUpdate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.price()).isEqualTo(bookToUpdate.price());
                });
    }

    @Test
    void whenPutRequestNonExistingBook_ThenBookCreated() {
        var bookIsbn = "1231231233";
        var bookToCreate = new Book(bookIsbn, "Title", "Author", 9.90);
        webTestClient
                .put()
                .uri("/books/" + bookIsbn)
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(book -> {
                    assertThat(book).isNotNull();
                    assertThat(book).usingRecursiveComparison().isEqualTo(bookToCreate);
                });
    }

    @Test
    void whenDeleteRequest_ThenBookDeleted() {
        var bookIsbn = "1231231234";
        var bookToCreate = new Book(bookIsbn, "Title", "Author", 9.90);
        webTestClient
                .post()
                .uri("/books")
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated();

        webTestClient
                .delete()
                .uri("/books/" + bookIsbn)
                .exchange()
                .expectStatus().isNoContent();

        webTestClient
                .get()
                .uri("/books/" + bookIsbn)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class).isEqualTo("Book with ISBN [" + bookIsbn + "] was not found.");
    }
}
