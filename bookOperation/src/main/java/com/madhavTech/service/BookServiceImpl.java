package com.madhavTech.service;
import com.madhavTech.exception.BookNotFoundException;
import com.madhavTech.exception.DuplicateIsbnException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.madhavTech.dto.BookRequest;
import com.madhavTech.dto.BookResponse;
import com.madhavTech.entity.Book;
import com.madhavTech.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;
    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }
    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Override
    public List<BookResponse> allBooks() {
        logger.info("SerViceImapl :: allBooks() getting all books here...");
        return repository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .toList();
    }
    @Override
    public BookResponse saveBook(BookRequest request) {
        logger.info("serviceImpl:: saveBook() saving book in db is :  {}",request.getBookName());
        Book books = convertDtoToEntity(request);
        Book savedBook = repository.save(books);
        return convertEntityToDto(savedBook);
    }

    @Override
    public BookResponse getOneBook(Long id) {
        logger.info("serviceImpl:: getOneBook() getting single book with this id : {}",id);
        Book book = findBookById(id);
        return convertEntityToDto(book);
    }

    @Override
    public void deleteBook(Long id) {
        logger.info("serviceImpl:: deleteBook() removing book from service {}",id);
        repository.deleteById(id);
    }

//    @Override
//    public BookResponse updateBook(BookRequest request, Long id) {
//        log.info("Request received to update book with ID: {}", id);
//        Book existingBook = findBookById(id);
//
//    }

    @Override
    public BookResponse updateBook(BookRequest request, Long id) {

        logger.info("Updating book with id: {}", id);

        // Step 1: Find existing book
        Book existingBook = repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Book not found for update, id: {}", id);
                    return new BookNotFoundException(
                            "Book not found with id: " + id
                    );
                });
            updateEntityFromDto(request,existingBook);
        // Step 3: Save updated entity
        Book updatedBook = repository.save(existingBook);
        logger.info("Book updated successfully, id: {}",
                updatedBook.getId());

        // Step 4: Entity -> DTO
        return convertEntityToDto(updatedBook);
    }

    // COMMON FIND-BY-ID METHOD
    private Book findBookById(Long id) {
        Book books = repository.findById(id).orElseThrow(()->
                new BookNotFoundException("Book not found on server with this id :"+id));
        return books;
//        return repository.findById(id)
//                .orElseThrow(() -> {
//                    log.warn("Book not found with ID: {}", id);
//                    return new BookNotFoundException("Book not found with ID: " + id);
//                });
    }
    @Override
    public List<BookResponse> findBooksGreaterThanPrice(Double price){
        logger.info("fetching books which price is greater than price : {}",price);
        List<Book> pricedBooks = repository.findBooksByGreaterThanPrice(price);
        logger.info("Found {} books with price greater than {}",pricedBooks.size(),price);
        return pricedBooks.stream().map(this::convertEntityToDto).toList();
    }


    //converting Entity to DTO
    private BookResponse convertEntityToDto(Book book){
        BookResponse response = new BookResponse();
        response.setAuthor(book.getAuthor());
        response.setBookName(book.getBookName());
        response.setCategory(book.getCategory());
        response.setIsbn(book.getIsbn());
        response.setPrice(book.getPrice());
        response.setQuantity(book.getQuantity());
        response.setId(book.getId());
        return response;
    }
    //converting DTO to Entity
    private Book convertDtoToEntity(BookRequest request){
        Book book = new Book();
        book.setAuthor(request.getAuthor());
        book.setBookName(request.getBookName());
        book.setCategory(request.getCategory());
        book.setIsbn(request.getIsbn());
        book.setQuantity(request.getQuantity());
        book.setPrice(request.getPrice());
        return book;
    }
    private void updateEntityFromDto(
            BookRequest request,
            Book book) {

        book.setBookName(request.getBookName());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPrice(request.getPrice());
        book.setQuantity(request.getQuantity());
        book.setCategory(request.getCategory());
    }
}
