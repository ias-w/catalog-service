// 3.3.1.4 USING EXCEPTIONS TO SIGNAL ERRORS IN THE DOMAIN

package dev.ilkersahin.catalogservice.domain;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String isbn) {
        super("Book with ISBN [" + isbn + "] was not found.");
    }
}
