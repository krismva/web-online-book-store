package web.app.onlinebookshop.repository.category;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import web.app.onlinebookshop.model.Book;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Sql(scripts = "classpath:database/book/add-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/remove-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get all books by existing category")
    public void getBooks_ExistingCategoryId_expectedTwo() {
        List<Book> actualBooks = categoryRepository.getBooksByCategoriesId(2L);
        Assertions.assertNotNull(actualBooks);
        Assertions.assertEquals(2, actualBooks.size());
    }

    @Test
    @Sql(scripts = "classpath:database/book/add-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/remove-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get all books by existing category")
    public void getBooks_ExistingCategoryId_expectedOne() {
        List<Book> actualBooks = categoryRepository.getBooksByCategoriesId(1L);
        Assertions.assertNotNull(actualBooks);
        Assertions.assertEquals(1, actualBooks.size());
    }

    @Test
    @DisplayName("Get books by non existing category")
    public void getBooks_NonExistingCategoryId_expectedEmpty() {
        List<Book> actualBooks = categoryRepository.getBooksByCategoriesId(777L);
        Assertions.assertNotNull(actualBooks);
        Assertions.assertEquals(0, actualBooks.size());
    }

    @Test
    @DisplayName("Get books that does not belong to the category")
    public void getBooks_ExistingCategoryButBooksNotBelong_expectedEmpty() {
        List<Book> actualBooks = categoryRepository.getBooksByCategoriesId(3L);
        Assertions.assertNotNull(actualBooks);
        Assertions.assertEquals(0, actualBooks.size());
    }
}
