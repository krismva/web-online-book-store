package web.app.onlinebookshop.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import javax.sql.DataSource;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import web.app.onlinebookshop.dto.book.BookDto;
import web.app.onlinebookshop.dto.book.CreateBookRequestDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public static void beforeAll(@Autowired DataSource dataSource,
            @Autowired WebApplicationContext applicationContext) throws SQLException {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(connection,
                    new ClassPathResource("database/book/add-books-and-categories.sql"));
        }
    }

    @WithMockUser
    @Test
    @DisplayName("Get all books, expected size 2")
    public void getAllBooks_expectedTwo_ok() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/books"))
                .andExpect(status().isOk())
                .andReturn();
        List<BookDto> actual = objectMapper.readerForListOf(BookDto.class)
                .readValue(mvcResult.getResponse().getContentAsString());

        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @WithMockUser
    @Test
    @DisplayName("Get book by id")
    public void getBook_byId_ok() throws Exception {
        BookDto expected = new BookDto()
                .setTitle("Kobzar")
                .setAuthor("Taras Shevchenko")
                .setIsbn("555555_5")
                .setPrice(BigDecimal.valueOf(119.99));

        MvcResult mvcResult = mockMvc.perform(
                        get("/api/books/1"))
                .andExpect(status().isOk())
                .andReturn();
        BookDto actual = objectMapper.readValue(mvcResult
                .getResponse().getContentAsString(), BookDto.class);

        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser
    @Test
    @DisplayName("Get book by not existing id")
    public void getBook_byNotExistingId_NotOk() throws Exception {
        mockMvc.perform(
                get("/api/books/777"))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Sql(scripts = "classpath:database/book/remove-test-book.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Create a book")
    public void createBook_ValidRequest_ok() throws Exception {
        CreateBookRequestDto requestDto = createTestBook();
        BookDto expectedBookDto = toBookDto(requestDto);

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                post("/api/books")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BookDto actualBookDto = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);

        Assertions.assertNotNull(actualBookDto);
        Assertions.assertNotNull(actualBookDto.getId());
        EqualsBuilder.reflectionEquals(expectedBookDto, actualBookDto, "id");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:database/book/add-book-with-id-7.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/remove-book-with-id-7.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Delete book by id 7")
    public void deleteBook_ById_7_Ok() throws Exception {
        mockMvc.perform(
                delete("/api/books/{id}", 7L))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Delete book by wrong id")
    public void deleteBook_ByWrongId_notOk() throws Exception {
        mockMvc.perform(
                delete("/api/books/{id}", 777L))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Sql(scripts = "classpath:database/book/add-book-with-id-7.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/remove-book-with-id-7.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Update existing book")
    public void updateBookTitle_ValidRequest_ok() throws Exception {
        CreateBookRequestDto requestDto = createTestBook();
        BookDto expectedBookDto = toBookDto(requestDto);

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        put("/api/books/7")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actualBookDto = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);

        Assertions.assertNotNull(actualBookDto);
        Assertions.assertNotNull(actualBookDto.getId());
        EqualsBuilder.reflectionEquals(expectedBookDto, actualBookDto);
    }

    @WithMockUser
    @Test
    @DisplayName("Search book by existing title")
    public void searchBook_ByTitle_ExpectedOne_ok() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/books/search")
                                .param("title", "ava"))
                .andExpect(status().isOk())
                .andReturn();

        List<BookDto> actual = objectMapper.readerForListOf(BookDto.class)
                .readValue(mvcResult.getResponse().getContentAsString());
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals("Thinking Java", actual.get(0).getTitle());
    }

    @WithMockUser
    @Test
    @DisplayName("Search book by existing title, expected 2")
    public void searchBook_ByTitle_ExpectedTwo_ok() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/books/search")
                                .param("title", "a"))
                .andExpect(status().isOk())
                .andReturn();

        List<BookDto> actual = objectMapper.readerForListOf(BookDto.class)
                .readValue(mvcResult.getResponse().getContentAsString());
        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @WithMockUser
    @Test
    @DisplayName("Search book by author")
    public void searchBook_ByAuthor_ExpectedOne_ok() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/books/search")
                                .param("author", "taras"))
                .andExpect(status().isOk())
                .andReturn();

        List<BookDto> actual = objectMapper.readerForListOf(BookDto.class)
                .readValue(mvcResult.getResponse().getContentAsString());
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals("Taras Shevchenko", actual.get(0).getAuthor());
    }

    private BookDto toBookDto(CreateBookRequestDto requestDto) {
        return new BookDto()
                .setTitle(requestDto.getTitle())
                .setAuthor(requestDto.getAuthor())
                .setIsbn(requestDto.getIsbn())
                .setPrice(requestDto.getPrice())
                .setCategoryIds(requestDto.getCategoryIds());
    }

    private CreateBookRequestDto createTestBook() {
        return new CreateBookRequestDto()
                .setTitle("Test Book")
                .setAuthor("Test Author")
                .setIsbn("testtest")
                .setPrice(BigDecimal.valueOf(90.99))
                .setCategoryIds(Set.of(3L));
    }
}
