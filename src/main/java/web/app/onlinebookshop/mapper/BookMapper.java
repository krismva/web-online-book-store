package web.app.onlinebookshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import web.app.onlinebookshop.config.MapperConfig;
import web.app.onlinebookshop.dto.BookDto;
import web.app.onlinebookshop.dto.CreateBookRequestDto;
import web.app.onlinebookshop.model.Book;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    @Mapping(target = "id", ignore = true)
    Book toModel(CreateBookRequestDto requestDto);
}
