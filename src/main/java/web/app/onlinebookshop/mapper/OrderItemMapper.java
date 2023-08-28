package web.app.onlinebookshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import web.app.onlinebookshop.config.MapperConfig;
import web.app.onlinebookshop.dto.order.OrderItemDto;
import web.app.onlinebookshop.dto.order.OrderItemRequestDto;
import web.app.onlinebookshop.model.CartItem;
import web.app.onlinebookshop.model.OrderItem;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "orderItem.book.id")
    OrderItemDto orderItemToDto(OrderItem orderItem);

    @Mapping(target = "id", ignore = true)
    OrderItem toModel(OrderItemRequestDto requestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", source = "cartItem.book.price")
    OrderItem cartItemToOrderItem(CartItem cartItem);
}
