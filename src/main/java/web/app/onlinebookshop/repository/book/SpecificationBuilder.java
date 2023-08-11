package web.app.onlinebookshop.repository.book;

import org.springframework.data.jpa.domain.Specification;
import web.app.onlinebookshop.dto.book.BookSearchParameters;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookSearchParameters searchParameters);
}
