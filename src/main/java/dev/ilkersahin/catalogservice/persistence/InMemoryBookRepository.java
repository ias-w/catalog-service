// 3.3.1.3 USING THE REPOSITORY ABSTRACTION FOR DATA ACCESS

package dev.ilkersahin.catalogservice.persistence;

import dev.ilkersahin.catalogservice.domain.Book;
import dev.ilkersahin.catalogservice.domain.BookRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryBookRepository implements BookRepository {
    private static final Map<String, Book> books = new ConcurrentHashMap<>();

    @Override
    public Iterable<Book> findAll() {
        return books.values();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return existsByIsbn(isbn) ? Optional.of(books.get(isbn)) : Optional.empty();
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return books.get(isbn) != null;
    }

    @Override
    public Book save(Book book) {
        books.put(book.isbn(), book);
        return book;
    }

    @Override
    public void deleteByIsbn(String isbn) {
        books.remove(isbn);
    }
}

// While the repository interface belongs to the domain,
// its implementation is part of the persistence layer.
// We’ll add a data persistence layer using a relational database in chapter 5.
// For now, it’s enough to add a simple in-memory map to retrieve and save books.
// You can define the implementation in an InMemoryBookRepository class,
// located in a new com.polarbookshop.catalogservice.persistence package.
// Listing 3.7 In-memory implementation of the BookRepository interface
