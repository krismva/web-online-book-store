package web.app.onlinebookshop.repository;

import java.util.List;
import java.util.Optional;
import web.app.onlinebookshop.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();

    Optional<Book> findById(Long id);
}
