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
import java.util.ArrayList;
import java.util.List;
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
import web.app.onlinebookshop.dto.book.BookDtoWithoutCategoryIds;
import web.app.onlinebookshop.dto.category.CategoryDto;
import web.app.onlinebookshop.dto.category.CreateCategoryRequestDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public static void beforeAll(@Autowired DataSource dataSource,
                                 @Autowired WebApplicationContext applicationContext)
                                 throws SQLException {
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

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Sql(scripts = "classpath:database/category/remove-test-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Create a category")
    public void createCategory_ValidRequest_ok() throws Exception {
        CreateCategoryRequestDto requestDto = createTestCategory();
        CategoryDto expectedCategoryDto = categoryToDto(requestDto);

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        post("/api/categories")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        CategoryDto actualCategoryDto = objectMapper
                .readValue(result.getResponse().getContentAsString(),
                CategoryDto.class);

        Assertions.assertNotNull(actualCategoryDto);
        Assertions.assertNotNull(actualCategoryDto.getId());
        EqualsBuilder.reflectionEquals(expectedCategoryDto, actualCategoryDto, "id");
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Create a category without required field Name, expected BAD_REQUEST")
    public void createCategory_WithoutName_NotOk() throws Exception {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto()
                .setDescription("Description delete_me");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(
                        post("/api/categories")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @WithMockUser
    @Test
    @DisplayName("Get all categories, expected size 3")
    public void getAllCategories_expectedThree_ok() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/categories"))
                .andExpect(status().isOk())
                .andReturn();
        List<CategoryDto> actual = objectMapper.readerForListOf(CategoryDto.class)
                .readValue(mvcResult.getResponse().getContentAsString());

        assertNotNull(actual);
        assertEquals(3, actual.size());
    }

    @WithMockUser
    @Test
    @DisplayName("Get category by id")
    public void getCategory_byId_ok() throws Exception {
        CategoryDto expected = new CategoryDto()
                .setId(1L)
                .setName("TestCategory1")
                .setDescription("Test description");

        MvcResult mvcResult = mockMvc.perform(
                        get("/api/categories/1"))
                .andExpect(status().isOk())
                .andReturn();
        CategoryDto actual = objectMapper.readValue(mvcResult
                .getResponse().getContentAsString(), CategoryDto.class);

        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @WithMockUser
    @Test
    @DisplayName("Get category by not existing id")
    public void getCategory_byNotExistingId_NotOk() throws Exception {
        mockMvc.perform(
                        get("/api/categories/777"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:database/category/add-category-with-id-5.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/category/remove-category-with-id-5.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Delete category by id 5")
    public void deleteCategory_ById_5_Ok() throws Exception {
        mockMvc.perform(
                        delete("/api/categories/{id}", 5L))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Delete category by wrong id")
    public void deleteCategory_ByWrongId_notOk() throws Exception {
        mockMvc.perform(
                        delete("/api/categories/{id}", 777L))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Sql(scripts = "classpath:database/category/add-category-with-id-5.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/category/remove-category-with-id-5.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Update existing category")
    public void updateCategory_ValidRequest_ok() throws Exception {
        CreateCategoryRequestDto requestDto = createTestCategory();
        CategoryDto expectedCategoryDto = categoryToDto(requestDto);

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        put("/api/categories/5")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actualCategoryDto = objectMapper
                .readValue(result.getResponse().getContentAsString(),
                CategoryDto.class);

        Assertions.assertNotNull(actualCategoryDto);
        Assertions.assertNotNull(actualCategoryDto.getId());
        EqualsBuilder.reflectionEquals(expectedCategoryDto, actualCategoryDto);
    }

    @WithMockUser
    @Test
    @DisplayName("Get all books by category Id")
    public void getAllBooks_ByCategoryId_ExpectedTwo_ok() throws Exception {
        List<BookDtoWithoutCategoryIds> expectedBooks = new ArrayList<>();
        expectedBooks.add(new BookDtoWithoutCategoryIds()
                .setId(1L)
                .setTitle("Kobzar")
                .setAuthor("Taras Shevchenko")
                .setIsbn("555555_5")
                .setPrice(BigDecimal.valueOf(119.99)));
        expectedBooks.add(new BookDtoWithoutCategoryIds()
                .setId(2L)
                .setTitle("Thinking Java")
                .setAuthor("Bruce Eckel")
                .setIsbn("6666666_6")
                .setPrice(BigDecimal.valueOf(99.99)));

        MvcResult mvcResult = mockMvc.perform(
                        get("/api/categories/2/books"))
                .andExpect(status().isOk())
                .andReturn();

        List<BookDtoWithoutCategoryIds> actual = objectMapper
                .readerForListOf(BookDtoWithoutCategoryIds.class)
                .readValue(mvcResult.getResponse().getContentAsString());
        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals(expectedBooks, actual);
    }

    private CreateCategoryRequestDto createTestCategory() {
        return new CreateCategoryRequestDto()
                .setName("Category_delete_me")
                .setDescription("Description delete_me");
    }

    private CategoryDto categoryToDto(CreateCategoryRequestDto requestDto) {
        return new CategoryDto()
                .setName(requestDto.getName())
                .setDescription(requestDto.getDescription());
    }
}
