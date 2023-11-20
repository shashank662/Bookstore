package com.example.bookstore.Controller;

import com.example.bookstore.Entity.AuthRequest;
import com.example.bookstore.Entity.Book;
import com.example.bookstore.Repository.BookRepository;
import com.example.bookstore.Service.BookService;
import com.example.bookstore.Entity.UserInfo;
import com.example.bookstore.Service.JwtService;
import com.example.bookstore.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepo;

//    @GetMapping("/welcome")
//    public String welcome() {
//        return "Welcome this endpoint is not secure";
//    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo);
    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @PostMapping("/user/buyBook")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String buyBook(@RequestParam String title) {
        Optional<Book> optionalBook = bookRepo.findByTitle(title);

        if (optionalBook.isPresent()) {
            return "Book found. Will be added";
        } else {
            return "Book not found. Try changing the title and try again";
        }
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @GetMapping("/admin/")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<UserInfo>> getUsers() {
        return ResponseEntity.ok(service.getUsers());
    }

    @PostMapping("/addUserAdmin")
    public String addUserAsAdmin(@RequestBody UserInfo userInfo) {
        return service.addUserAdmin(userInfo);
    }
    @PostMapping("/admin/addBook")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.createNewBook(book));
    }
    @PutMapping("/admin/updateBook/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book title) {
        title.setId(id);
        return ResponseEntity.ok(bookService.updateBook(title));
    }
    @DeleteMapping("/admin/deleteBook/{id}")
    public ResponseEntity<Boolean> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request!");
        }
    }

}

