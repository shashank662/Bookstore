package com.example.bookstore.Repository;

import com.example.bookstore.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
//    public Book findByBook(String title);

    public Book getById(Long id);

    public void deleteById(Long id);

    Optional<Book> findByTitle(String title);
}
