package web.app.onlinebookshop.repository.book.spec;

import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import web.app.onlinebookshop.model.Book;
import web.app.onlinebookshop.repository.SpecificationProvider;

@Component
public class PriceSpecificationProvider implements SpecificationProvider<Book> {
    private static final String PRICE_KEY = "price";

    @Override
    public String getKey() {
        return PRICE_KEY;
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) ->
                root.get(PRICE_KEY).in(Arrays.stream(params).toArray());
    }
}
