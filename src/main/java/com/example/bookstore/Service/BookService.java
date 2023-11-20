package com.example.bookstore.Service;

import com.example.bookstore.Entity.Book;
import com.example.bookstore.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book createNewBook(Book book){ return bookRepository.save(book);}

    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }


    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

}
