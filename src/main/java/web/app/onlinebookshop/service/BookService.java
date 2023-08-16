package web.app.onlinebookshop.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import web.app.onlinebookshop.dto.book.BookDto;
import web.app.onlinebookshop.dto.book.BookSearchParameters;
import web.app.onlinebookshop.dto.book.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto update(Long id, CreateBookRequestDto requestDto);

    List<BookDto> search(BookSearchParameters searchParameters);
}
