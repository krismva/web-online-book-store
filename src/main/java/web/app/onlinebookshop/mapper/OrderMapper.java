package web.app.onlinebookshop.mapper;

import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import web.app.onlinebookshop.config.MapperConfig;
import web.app.onlinebookshop.dto.order.OrderDto;
import web.app.onlinebookshop.dto.order.UpdateOrderStatusRequestDto;
import web.app.onlinebookshop.model.Order;
import web.app.onlinebookshop.model.ShoppingCart;

@Mapper(config = MapperConfig.class)
public abstract class OrderMapper {
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Mapping(target = "orderItems", ignore = true)
    public abstract OrderDto orderToDto(Order order);

    @Mapping(target = "id", ignore = true)
    public abstract Order updateStatusToModel(UpdateOrderStatusRequestDto requestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "status", expression = "java(web.app.onlinebookshop.model.Status.PENDING)")
    @Mapping(target = "orderDate", expression = "java(java.time.LocalDateTime.now())")
    public abstract Order toOrderFromCart(ShoppingCart shoppingCart);

    @AfterMapping
    public void setUserId(@MappingTarget OrderDto orderDto, Order order) {
        orderDto.setUserId(order.getUser().getId());
    }

    @AfterMapping
    public void setOrderItemsToDto(@MappingTarget OrderDto orderResponseDto, Order order) {
        orderResponseDto.setOrderItems(order.getOrderItems()
                .stream()
                .map(orderItemMapper::orderItemToDto)
                .collect(Collectors.toSet()));
    }
}
