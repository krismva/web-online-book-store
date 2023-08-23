package web.app.onlinebookshop.service;

import java.util.Set;
import web.app.onlinebookshop.dto.cart.CartItemDto;
import web.app.onlinebookshop.dto.cart.CreateCartItemRequestDto;
import web.app.onlinebookshop.dto.cart.UpdateQuantityDto;
import web.app.onlinebookshop.dto.cart.UpdateQuantityRequestDto;
import web.app.onlinebookshop.model.CartItem;

public interface CartItemService {
    CartItemDto save(CreateCartItemRequestDto requestDto);

    UpdateQuantityDto update(Long id, UpdateQuantityRequestDto requestDto);

    void delete(Long id);

    Set<CartItemDto> findCartItemsByShoppingCartId(Long id);

    CartItem findById(Long id);
}
