package web.app.onlinebookshop.service;

import web.app.onlinebookshop.dto.cart.CartItemDto;
import web.app.onlinebookshop.dto.cart.CreateCartItemRequestDto;
import web.app.onlinebookshop.dto.cart.ShoppingCartDto;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart();

    CartItemDto addBook(CreateCartItemRequestDto requestDto);

}
