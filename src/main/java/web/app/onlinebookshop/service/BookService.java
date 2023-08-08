package web.app.onlinebookshop.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import web.app.onlinebookshop.dto.BookDto;
import web.app.onlinebookshop.dto.BookSearchParameters;
import web.app.onlinebookshop.dto.CreateBookRequestDto;
import web.app.onlinebookshop.model.Book;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto update(Book book);

    List<BookDto> search(BookSearchParameters searchParameters);
}
