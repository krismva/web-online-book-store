package web.app.onlinebookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.app.onlinebookshop.dto.order.OrderDto;
import web.app.onlinebookshop.dto.order.OrderItemDto;
import web.app.onlinebookshop.dto.order.PlaceOrderRequestDto;
import web.app.onlinebookshop.dto.order.UpdateOrderStatusRequestDto;
import web.app.onlinebookshop.service.OrderItemService;
import web.app.onlinebookshop.service.OrderService;

@Tag(name = "Order CRUD operations", description = "Endpoints for order")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @Operation(summary = "Get all user's orders", description = "Get a list of all orders")
    @GetMapping
    public List<OrderDto> getAll(Pageable pageable) {
        return orderService.findAll(pageable);
    }

    @Operation(summary = "Place order", description = "Place order")
    @PostMapping
    public OrderDto placeOrder(@RequestBody @Valid PlaceOrderRequestDto requestDto) {
        return orderService.placeOrder(requestDto);
    }

    @Operation(summary = "Update status of order",
            description = "Update status of order(Admin only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    public OrderDto updateStatus(@PathVariable Long id,
                                 @RequestBody @Valid UpdateOrderStatusRequestDto requestDto) {
        return orderService.updateStatus(id, requestDto);
    }

    @Operation(summary = "Get all items in specific order",
            description = "Get all items in specific order")
    @GetMapping("/{orderId}/items")
    public List<OrderItemDto> getOrderItems(@PathVariable Long orderId) {
        return orderItemService.findAllByOrderId(orderId);
    }

    @Operation(summary = "Get specific item from the order",
            description = "Get specific item from the order")
    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemDto getSpecificItemByOrderId(@PathVariable Long orderId,
                                                 @PathVariable Long itemId) {
        return orderItemService.findOrderItemByOrderIdAndItemId(orderId, itemId);
    }

}
