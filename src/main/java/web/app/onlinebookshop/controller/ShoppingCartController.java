package web.app.onlinebookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.app.onlinebookshop.dto.cart.CartItemDto;
import web.app.onlinebookshop.dto.cart.CreateCartItemRequestDto;
import web.app.onlinebookshop.dto.cart.ShoppingCartDto;
import web.app.onlinebookshop.dto.cart.UpdateQuantityDto;
import web.app.onlinebookshop.dto.cart.UpdateQuantityRequestDto;
import web.app.onlinebookshop.service.CartItemService;
import web.app.onlinebookshop.service.ShoppingCartService;

@Tag(name = "Shopping Cart CRUD operations", description = "Endpoints for shopping cart")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;

    @Operation(summary = "Get user's shopping cart", description = "Get user's shopping cart")
    @GetMapping
    public ShoppingCartDto getShoppingCart() {
        return shoppingCartService.getShoppingCart();
    }

    @Operation(summary = "Add a book to the shopping cart",
            description = "Add a book to the shopping cart")
    @PostMapping
    public CartItemDto addBook(
            @RequestBody @Valid CreateCartItemRequestDto requestDto) {
        return shoppingCartService.addBook(requestDto);
    }

    @Operation(summary = "Update quantity of books",
            description = "Update quantity of books in the shopping cart")
    @PutMapping("/cart-items/{cartItemId}")
    public UpdateQuantityDto updateQuantity(@PathVariable Long cartItemId,
                                     @RequestBody @Valid UpdateQuantityRequestDto requestDto) {
        return cartItemService.update(cartItemId, requestDto);
    }

    @Operation(summary = "Delete a books from the shopping cart",
            description = "Delete a books from the shopping cart")
    @DeleteMapping("/cart-items/{cartItemId}")
    public void deleteBooks(@PathVariable Long cartItemId) {
        cartItemService.delete(cartItemId);
    }

}
