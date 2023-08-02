package web.app.onlinebookshop.service;

import java.util.List;
import web.app.onlinebookshop.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
