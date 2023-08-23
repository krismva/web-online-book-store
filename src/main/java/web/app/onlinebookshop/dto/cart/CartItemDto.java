package web.app.onlinebookshop.dto.cart;

import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private Long shoppingCartId;
    private Long bookId;
    private String bookTitle;
    private int quantity;
}
