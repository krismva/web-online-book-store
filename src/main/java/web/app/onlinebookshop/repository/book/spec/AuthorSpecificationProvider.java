package web.app.onlinebookshop.repository.book.spec;

import jakarta.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import web.app.onlinebookshop.model.Book;
import web.app.onlinebookshop.repository.SpecificationProvider;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    private static final String AUTHOR_KEY = "author";

    @Override
    public String getKey() {
        return AUTHOR_KEY;
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = Arrays.stream(params)
                    .filter(Objects::nonNull)
                    .map(partialTitle -> {
                        String pattern = "%" + partialTitle.toLowerCase() + "%";
                        return criteriaBuilder.like(criteriaBuilder
                                .lower(root.get(AUTHOR_KEY)), pattern);
                    }).toList();

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
}
