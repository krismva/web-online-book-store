package web.app.onlinebookshop.repository.category;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.app.onlinebookshop.model.Book;
import web.app.onlinebookshop.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT b FROM Book b JOIN b.categories c WHERE c.id = :id")
    List<Book> getBooksByCategoriesId(Long id);

}
