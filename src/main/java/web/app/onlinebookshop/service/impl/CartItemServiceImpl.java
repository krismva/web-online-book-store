package web.app.onlinebookshop.service.impl;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.app.onlinebookshop.dto.cart.CartItemDto;
import web.app.onlinebookshop.dto.cart.CreateCartItemRequestDto;
import web.app.onlinebookshop.dto.cart.UpdateQuantityDto;
import web.app.onlinebookshop.dto.cart.UpdateQuantityRequestDto;
import web.app.onlinebookshop.exception.EntityNotFoundException;
import web.app.onlinebookshop.mapper.CartItemMapper;
import web.app.onlinebookshop.model.CartItem;
import web.app.onlinebookshop.model.ShoppingCart;
import web.app.onlinebookshop.model.User;
import web.app.onlinebookshop.repository.book.BookRepository;
import web.app.onlinebookshop.repository.cart.CartItemRepository;
import web.app.onlinebookshop.repository.cart.ShoppingCartRepository;
import web.app.onlinebookshop.service.CartItemService;
import web.app.onlinebookshop.service.UserService;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final BookRepository bookRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;

    @Override
    public CartItemDto save(CreateCartItemRequestDto requestDto) {
        User user = userService.getUser();
        CartItem cartItem = new CartItem();
        cartItem.setBook(bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find book by id " + requestDto.getBookId())));
        cartItem.setShoppingCart(shoppingCartRepository
                .getShoppingCartByUserId(user.getId()));
        cartItem.setQuantity(requestDto.getQuantity());
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public UpdateQuantityDto update(Long id, UpdateQuantityRequestDto requestDto) {
        verifyCartItem(id);
        CartItem cartItem = findById(id);
        cartItem.setId(id);
        cartItem.setQuantity(requestDto.getQuantity());
        return cartItemMapper.updateToDto(cartItemRepository.save(cartItem));
    }

    @Override
    public void delete(Long id) {
        verifyCartItem(id);
        CartItem cartItem = findById(id);
        cartItem.setId(id);
        cartItem.setQuantity(0);
        cartItemRepository.save(cartItem);
        cartItemRepository.deleteById(id);
    }

    @Override
    public Set<CartItemDto> findCartItemsByShoppingCartId(Long id) {
        return cartItemRepository.findCartItemsByShoppingCartId(id)
                .stream()
                .map(cartItemMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public CartItem findById(Long id) {
        return cartItemRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find cart item by id " + id));
    }

    private boolean isCartItemBelongsToUser(Long cartItemId) {
        ShoppingCart shoppingCart = shoppingCartRepository
                .getShoppingCartByUserId(userService.getUser().getId());
        Set<CartItem> cartItems = cartItemRepository
                .findCartItemsByShoppingCartId(shoppingCart.getId());
        return cartItems.stream()
                .anyMatch(cartItem -> cartItem.getId().equals(cartItemId));
    }

    private void verifyCartItem(Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId) || !isCartItemBelongsToUser(cartItemId)) {
            throw new EntityNotFoundException("Can't find cart item by id " + cartItemId);
        }
    }
}
