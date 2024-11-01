// 3.3.1.1 DEFINING THE DOMAIN ENTITY
// 3.3.3 Data validation and error handling
// 5.2.2 Defining persistent entities with Spring Data
// 5.2.3 Enabling and configuring JDBC auditing
// 5.4.3 Evolving a database with Flyway

package dev.ilkersahin.catalogservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.time.Instant;

public record Book(
        @Id
        Long id,

        @NotBlank(message = "The book ISBN must be defined.")
        @Pattern(
                regexp = "^([0-9]{10}|[0-9]{13})$",
                message = "The ISBN format must be valid."
        )
        String isbn,

        @NotBlank(message = "The book title must be defined.")
        String title,

        @NotBlank(message = "The book author must be defined.")
        String author,

        @NotNull(message = "The book price must be defined.")
        @Positive(message = "The book price must be greater than zero.")
        Double price,

        String publisher,

        @CreatedDate
        Instant createdDate,

        @LastModifiedDate
        Instant lastModifiedDate,

        @Version
        int version
) {
    public static Book of(String isbn, String title, String author, Double price) {
        return Book.of(isbn, title, author, price, null);
    }

    public static Book of(String isbn, String title, String author, Double price, String publisher) {
        return new Book(
                null,
                isbn, title, author, price, publisher,
                null, null, 0
        );
    }
}

// NOTE Books are uniquely identified by their ISBN (International Standard Book Number).
// ISBNs used to be composed of 10 digits, but they now consist of 13.
// For simplicity, we’ll limit ourselves to checking for their length and
// whether all the elements are digits by using a regular expression.

// NOTE Spring Data JPA works with mutating objects, so you can’t use Java records.
// JPA entity classes must be marked with the @Entity annotation and expose a no-args constructor.
// JPA identifiers are annotated with @Id and @Version from the javax.persistence package
// instead of org.springframework.data.annotation.
