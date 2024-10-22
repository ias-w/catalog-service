// 3.3.1.3 USING THE REPOSITORY ABSTRACTION FOR DATA ACCESS

package dev.ilkersahin.catalogservice.domain;

import java.util.Optional;

public interface BookRepository {
    Iterable<Book> findAll();

    Optional<Book> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    Book save(Book book);

    void deleteByIsbn(String isbn);
}

// The BookService class relies on a BookRepository object to
// retrieve and save books. The domain layer should be unaware
// of how data is persisted, so BookRepository should be an interface
// to decouple the abstraction from the actual implementation.
// Create a BookRepository interface in the
// com.polarbookshop.catalogservice.domain
// package to define the abstraction for accessing book data.
// Listing 3.6 The abstraction used by the domain layer to access data
