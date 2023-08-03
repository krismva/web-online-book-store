package web.app.onlinebookshop.service.impl;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import web.app.onlinebookshop.dto.BookDto;
import web.app.onlinebookshop.dto.CreateBookRequestDto;
import web.app.onlinebookshop.exception.EntityNotFoundException;
import web.app.onlinebookshop.mapper.BookMapper;
import web.app.onlinebookshop.model.Book;
import web.app.onlinebookshop.repository.BookRepository;
import web.app.onlinebookshop.service.BookService;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto bookRequestDto) {
        Book book = bookMapper.toModel(bookRequestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Could not find book with id " + id)));
    }
}
