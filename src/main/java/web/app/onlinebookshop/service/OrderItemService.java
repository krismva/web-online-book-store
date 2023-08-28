package web.app.onlinebookshop.service;

import java.util.List;
import web.app.onlinebookshop.dto.order.OrderItemDto;

public interface OrderItemService {
    List<OrderItemDto> findAllByOrderId(Long orderId);

    OrderItemDto findOrderItemByOrderIdAndItemId(Long orderId, Long itemId);
}
