package com.madhavTech.service;
import java.util.List;

import com.madhavTech.dto.BookRequest;
import com.madhavTech.dto.BookResponse;
import org.springframework.data.domain.Page;

public interface BookService {
    List<BookResponse> allBooks();
    BookResponse saveBook(BookRequest book);
    BookResponse getOneBook(Long id);
    BookResponse updateBook(BookRequest request, Long id);
    void deleteBook(Long id);
    List<BookResponse> findBooksGreaterThanPrice(Double price);
    Page<BookResponse> getBooksWithPaginationAndSorting(int page, int size, String sortBy, String direction);
}
