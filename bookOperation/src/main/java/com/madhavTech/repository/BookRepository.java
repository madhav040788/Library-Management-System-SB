package com.madhavTech.repository;

import com.madhavTech.dto.BookResponse;
import com.madhavTech.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface  BookRepository extends JpaRepository<Book, Long> {
    BookResponse findByIsbn(BookResponse request);
    @Query("select b from Book b where b.price > :price")
    List<Book> findBooksByGreaterThanPrice(@Param("price")Double price);
}
