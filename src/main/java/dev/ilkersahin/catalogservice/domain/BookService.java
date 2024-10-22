// 3.3.1.2 IMPLEMENTING THE USE CASES

package dev.ilkersahin.catalogservice.domain;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Iterable<Book> viewBookList() {
        return bookRepository.findAll();
    }

    public Book viewBookDetails(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public Book addBookToCatalog(Book book) {
        if (bookRepository.existsByIsbn(book.isbn())) {
            throw new BookAlreadyExistsException(book.isbn());
        }
        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

// The distinction between 200 and 201 was made by wrapping the return type inside a ResponseEntity.
    public ResponseEntity<Book> editBookDetails(String isbn, Book book) {
        return bookRepository.findByIsbn(isbn)
                .map(existingBook -> {
// When editing the book, all the Book fields can be updated
// except the ISBN code, because it’s the entity identifier.
                    var bookToUpdate = new Book(
                            existingBook.isbn(),
                            book.title(),
                            book.author(),
                            book.price()
                    );
                    return ResponseEntity.ok()
                            .body(bookRepository.save(bookToUpdate));
                })
// When changing the details for a book not in the catalog yet,
// create a new book.
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(addBookToCatalog(book))
                );
    }
}
