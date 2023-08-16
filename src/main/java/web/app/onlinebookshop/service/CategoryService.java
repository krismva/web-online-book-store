package web.app.onlinebookshop.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import web.app.onlinebookshop.dto.book.BookDtoWithoutCategoryIds;
import web.app.onlinebookshop.dto.category.CategoryDto;
import web.app.onlinebookshop.dto.category.CreateCategoryRequestDto;

public interface CategoryService {
    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto save(CreateCategoryRequestDto categoryDto);

    CategoryDto update(Long id, CreateCategoryRequestDto categoryDto);

    void deleteById(Long id);

    List<BookDtoWithoutCategoryIds> getBooksByCategoriesId(Long id);
}
