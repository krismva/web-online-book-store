package web.app.onlinebookshop.service;

import java.util.List;
import web.app.onlinebookshop.dto.BookDto;
import web.app.onlinebookshop.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);
}
