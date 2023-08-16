package web.app.onlinebookshop.mapper;

import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import web.app.onlinebookshop.config.MapperConfig;
import web.app.onlinebookshop.dto.book.BookDto;
import web.app.onlinebookshop.dto.book.BookDtoWithoutCategoryIds;
import web.app.onlinebookshop.dto.book.CreateBookRequestDto;
import web.app.onlinebookshop.model.Book;
import web.app.onlinebookshop.model.Category;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    @Mapping(target = "id", ignore = true)
    Book toModel(CreateBookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        bookDto.setCategoryIds(book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));
    }
}
