package web.app.onlinebookshop.repository.book;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import web.app.onlinebookshop.model.Book;
import web.app.onlinebookshop.repository.SpecificationProvider;
import web.app.onlinebookshop.repository.SpecificationProviderManager;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Can't find correct specification provider for key " + key));
    }
}
