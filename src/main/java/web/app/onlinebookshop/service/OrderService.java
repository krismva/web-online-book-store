package web.app.onlinebookshop.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import web.app.onlinebookshop.dto.order.OrderDto;
import web.app.onlinebookshop.dto.order.PlaceOrderRequestDto;
import web.app.onlinebookshop.dto.order.UpdateOrderStatusRequestDto;

public interface OrderService {
    OrderDto placeOrder(PlaceOrderRequestDto requestDto);

    List<OrderDto> findAll(Pageable pageable);

    OrderDto updateStatus(Long orderId, UpdateOrderStatusRequestDto requestDto);
}
