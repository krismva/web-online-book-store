package web.app.onlinebookshop.repository.book.spec;

import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import web.app.onlinebookshop.model.Book;
import web.app.onlinebookshop.repository.SpecificationProvider;

@Component
public class CoverImageSpecificationProvider implements SpecificationProvider<Book> {
    private static final String COVER_IMAGE_KEY = "coverImage";

    @Override
    public String getKey() {
        return COVER_IMAGE_KEY;
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) ->
                root.get(COVER_IMAGE_KEY).in(Arrays.stream(params).toArray());
    }
}
