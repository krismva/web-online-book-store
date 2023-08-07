package web.app.onlinebookshop.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.app.onlinebookshop.dto.BookDto;
import web.app.onlinebookshop.dto.BookSearchParameters;
import web.app.onlinebookshop.dto.CreateBookRequestDto;
import web.app.onlinebookshop.mapper.BookMapper;
import web.app.onlinebookshop.model.Book;
import web.app.onlinebookshop.service.BookService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping
    public List<BookDto> findAll() {
        return bookService.findAll();
    }

    @PostMapping
    public BookDto save(@RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @GetMapping("/{id}")
    public BookDto findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @PutMapping("/{id}")
    public BookDto update(@PathVariable Long id,
            @RequestBody @Valid CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        book.setId(id);
        bookService.update(book);
        return bookMapper.toDto(book);
    }

    @GetMapping("/search")
    public List<BookDto> search(BookSearchParameters searchParameters) {
        return bookService.search(searchParameters);
    }
}
