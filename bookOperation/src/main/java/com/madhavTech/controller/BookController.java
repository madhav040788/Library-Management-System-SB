package com.madhavTech.controller;
import  java.util.List;

import com.madhavTech.dto.BookRequest;
import com.madhavTech.dto.BookResponse;
import com.madhavTech.service.BookService;
import jakarta.validation.Valid;
import lombok.extern.flogger.Flogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final Logger logger = LoggerFactory.getLogger(BookController.class);
    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBook(){
        logger.info("BookController :: getting all book list: ");
        return new ResponseEntity<>(bookService.allBooks(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookResponse> addNewBook( @Valid @RequestBody BookRequest book){
        logger.info("BookController:: Request received to create book");
        BookResponse storeBook = bookService.saveBook(book);
        logger.info("Book created successfully with id: {}",storeBook.getId());
        return new ResponseEntity<>(storeBook,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id){
        logger.info("BookController:: getting single book by id :  {}",id);
        BookResponse books = bookService.getOneBook(id);
        return new ResponseEntity<>(books,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@Valid @RequestBody BookRequest request, @PathVariable Long id){
        logger.info("Update book request received for id: {}", id);
        BookResponse bookResponse = bookService.updateBook(request,id);
        logger.info("Update book request completed for id: {}",id);
        return ResponseEntity.ok(bookResponse);
    }
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id){
        logger.info("Your book is getting removed from server: {}",id);
        bookService.deleteBook(id);
        return "Removed Book from server of this Id : "+id;
    }

    @GetMapping("/price/{price}")
    public ResponseEntity<List<BookResponse>> getBooksByGreatherThanPrice(@PathVariable Double price){
        logger.info("controller :: getting books price greater than : {}",price);
        return ResponseEntity.ok(bookService.findBooksGreaterThanPrice(price));
    }
}
