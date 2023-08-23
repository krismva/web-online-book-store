package web.app.onlinebookshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.app.onlinebookshop.dto.cart.CartItemDto;
import web.app.onlinebookshop.dto.cart.CreateCartItemRequestDto;
import web.app.onlinebookshop.dto.cart.ShoppingCartDto;
import web.app.onlinebookshop.model.ShoppingCart;
import web.app.onlinebookshop.model.User;
import web.app.onlinebookshop.repository.cart.ShoppingCartRepository;
import web.app.onlinebookshop.service.CartItemService;
import web.app.onlinebookshop.service.ShoppingCartService;
import web.app.onlinebookshop.service.UserService;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemService cartItemService;
    private final UserService userService;

    @Override
    public ShoppingCartDto getShoppingCart() {
        ShoppingCart shoppingCart = getUserShoppingCart();
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setId(shoppingCart.getId());
        shoppingCartDto.setUserId(shoppingCart.getUser().getId());
        shoppingCartDto.setCartItems(cartItemService
                .findCartItemsByShoppingCartId(shoppingCart.getId()));
        return shoppingCartDto;
    }

    @Override
    public CartItemDto addBook(CreateCartItemRequestDto requestDto) {
        return cartItemService.save(requestDto);
    }

    private ShoppingCart getUserShoppingCart() {
        User user = userService.getUser();
        return shoppingCartRepository.getShoppingCartByUserId(user.getId());
    }
}
