package web.app.onlinebookshop.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import web.app.onlinebookshop.dto.book.BookDto;
import web.app.onlinebookshop.dto.book.BookSearchParameters;
import web.app.onlinebookshop.dto.book.CreateBookRequestDto;
import web.app.onlinebookshop.exception.EntityNotFoundException;
import web.app.onlinebookshop.mapper.BookMapper;
import web.app.onlinebookshop.model.Book;
import web.app.onlinebookshop.model.Category;
import web.app.onlinebookshop.repository.book.BookRepository;
import web.app.onlinebookshop.repository.book.BookSpecificationBuilder;
import web.app.onlinebookshop.repository.category.CategoryRepository;
import web.app.onlinebookshop.service.BookService;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto bookRequestDto) {
        Book book = bookMapper.toModel(bookRequestDto);
        getAllCategoriesByIds(bookRequestDto)
                .forEach(category -> category.addBook(book));

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Could not find book with id " + id)));
    }

    @Override
    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't delete book with id  " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto update(Long id, CreateBookRequestDto requestDto) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't find book by id: " + id);
        }
        Book book = bookMapper.toModel(requestDto);
        book.setId(id);
        book.setCategories(getAllCategoriesByIds(requestDto));
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> search(BookSearchParameters searchParameters) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(searchParameters);
        return bookRepository.findAll(bookSpecification)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    private Set<Category> getAllCategoriesByIds(CreateBookRequestDto requestDto) {
        return requestDto.getCategoryIds()
                .stream()
                .map(categoryRepository::findById)
                .map(category -> category.orElseThrow(() ->
                        new EntityNotFoundException("Can't find category: " + category)))
                .collect(Collectors.toSet());
    }
}
