package web.app.onlinebookshop.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.app.onlinebookshop.dto.order.OrderDto;
import web.app.onlinebookshop.dto.order.PlaceOrderRequestDto;
import web.app.onlinebookshop.dto.order.UpdateOrderStatusRequestDto;
import web.app.onlinebookshop.exception.EntityNotFoundException;
import web.app.onlinebookshop.mapper.OrderItemMapper;
import web.app.onlinebookshop.mapper.OrderMapper;
import web.app.onlinebookshop.model.CartItem;
import web.app.onlinebookshop.model.Order;
import web.app.onlinebookshop.model.OrderItem;
import web.app.onlinebookshop.model.ShoppingCart;
import web.app.onlinebookshop.model.User;
import web.app.onlinebookshop.repository.cart.CartItemRepository;
import web.app.onlinebookshop.repository.cart.ShoppingCartRepository;
import web.app.onlinebookshop.repository.order.OrderItemRepository;
import web.app.onlinebookshop.repository.order.OrderRepository;
import web.app.onlinebookshop.service.CartItemService;
import web.app.onlinebookshop.service.OrderService;
import web.app.onlinebookshop.service.UserService;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemService cartItemService;
    private final UserService userService;

    @Override
    public OrderDto placeOrder(PlaceOrderRequestDto requestDto) {
        User user = userService.getUser();
        ShoppingCart shoppingCart = shoppingCartRepository.getShoppingCartByUserId(user.getId());
        Set<CartItem> cartItems = cartItemRepository
                .findCartItemsByShoppingCartId(shoppingCart.getId());
        Set<OrderItem> orderItems = convertCartItemsToOrderItems(cartItems);

        Order order = orderMapper.toOrderFromCart(shoppingCart);
        order.setShippingAddress(requestDto.getShippingAddress());
        order.setTotal(getTotalPrice(orderItems));
        order.setOrderItems(orderItems);
        Order orderFromDB = orderRepository.save(order);

        orderItems.forEach(orderItem -> orderItem.setOrder(orderFromDB));
        orderItemRepository.saveAll(orderItems);

        cartItems.forEach(cartItem -> cartItemService.delete(cartItem.getId()));

        return orderMapper.orderToDto(orderFromDB);
    }

    @Override
    public List<OrderDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .stream()
                .map(orderMapper::orderToDto)
                .toList();
    }

    @Override
    public OrderDto updateStatus(Long orderId, UpdateOrderStatusRequestDto requestDto) {
        Order order = findById(orderId);
        order.setStatus(requestDto.getStatus());
        return orderMapper.orderToDto(orderRepository.save(order));
    }

    private Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find order by id " + id));
    }

    private Set<OrderItem> convertCartItemsToOrderItems(Set<CartItem> cartItems) {
        if (cartItems.isEmpty()) {
            throw new EntityNotFoundException("Shopping cart is empty! "
                    + "You need to add a book to continue");
        }
        return cartItems.stream()
                .map(orderItemMapper::cartItemToOrderItem)
                .collect(Collectors.toSet());
    }

    private BigDecimal getTotalPrice(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> orderItem.getPrice()
                        .multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
