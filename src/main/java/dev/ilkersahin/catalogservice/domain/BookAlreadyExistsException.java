// 3.3.1.4 USING EXCEPTIONS TO SIGNAL ERRORS IN THE DOMAIN

package dev.ilkersahin.catalogservice.domain;

public class BookAlreadyExistsException extends RuntimeException {
    public BookAlreadyExistsException(String isbn) {
        super("Book with ISBN [" + isbn + "] already exists.");
    }
}
