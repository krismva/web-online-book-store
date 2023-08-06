package web.app.onlinebookshop.repository.book;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import web.app.onlinebookshop.dto.BookSearchParameters;
import web.app.onlinebookshop.model.Book;
import web.app.onlinebookshop.repository.SpecificationProviderManager;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private static final String TITLE_KEY = "title";
    private static final String AUTHOR_KEY = "author";
    private static final String ISBN_KEY = "isbn";
    private static final String PRICE_KEY = "price";
    private static final String DESCRIPTION_KEY = "description";
    private static final String COVER_IMAGE_KEY = "coverImage";
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters searchParameters) {
        Specification<Book> spec = Specification.where(null);
        if (searchParameters.title() != null && searchParameters.title().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider(TITLE_KEY)
                    .getSpecification(searchParameters.title()));
        }
        if (searchParameters.author() != null && searchParameters.author().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider(AUTHOR_KEY)
                    .getSpecification(searchParameters.author()));
        }
        if (searchParameters.isbn() != null && searchParameters.isbn().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider(ISBN_KEY)
                    .getSpecification(searchParameters.isbn()));
        }
        if (searchParameters.price() != null && searchParameters.price().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider(PRICE_KEY)
                    .getSpecification(searchParameters.price()));
        }
        if (searchParameters.description() != null && searchParameters.description().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider(DESCRIPTION_KEY)
                    .getSpecification(searchParameters.description()));
        }
        if (searchParameters.coverImage() != null && searchParameters.coverImage().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider(COVER_IMAGE_KEY)
                    .getSpecification(searchParameters.coverImage()));
        }
        return spec;
    }
}
