package com.madhavTech.service;
import java.util.List;

import com.madhavTech.dto.BookRequest;
import com.madhavTech.dto.BookResponse;

public interface BookService {
    List<BookResponse> allBooks();
    BookResponse saveBook(BookRequest book);
    BookResponse getOneBook(Long id);
    BookResponse updateBook(BookRequest request, Long id);
    void deleteBook(Long id);
    List<BookResponse> findBooksGreaterThanPrice(Double price);
}
