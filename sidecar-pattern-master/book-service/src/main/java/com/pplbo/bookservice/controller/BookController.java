package com.pplbo.bookservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pplbo.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@Slf4j
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @SneakyThrows
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        log.info("BookController: addBook request {}", new ObjectMapper().writeValueAsString(book));
        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
    }

    @SneakyThrows
    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = bookService.getBooks();
        log.info("BookController: getBooks request {}", new ObjectMapper().writeValueAsString(books));
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
