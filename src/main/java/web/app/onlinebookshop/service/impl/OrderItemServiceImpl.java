package web.app.onlinebookshop.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.app.onlinebookshop.dto.order.OrderItemDto;
import web.app.onlinebookshop.exception.EntityNotFoundException;
import web.app.onlinebookshop.mapper.OrderItemMapper;
import web.app.onlinebookshop.repository.order.OrderItemRepository;
import web.app.onlinebookshop.service.OrderItemService;

@RequiredArgsConstructor
@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository itemRepository;
    private final OrderItemMapper itemMapper;

    @Override
    public List<OrderItemDto> findAllByOrderId(Long orderId) {
        return itemRepository.findAllByOrderId(orderId)
                .stream()
                .map(itemMapper::orderItemToDto)
                .toList();
    }

    @Override
    public OrderItemDto findOrderItemByOrderIdAndItemId(Long orderId, Long itemId) {
        return itemMapper.orderItemToDto(itemRepository
                .findOrderItemByOrderIdAndId(orderId, itemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find order item by id " + itemId)));
    }
}
