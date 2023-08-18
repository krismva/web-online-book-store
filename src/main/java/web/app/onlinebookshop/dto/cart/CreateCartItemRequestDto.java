package web.app.onlinebookshop.dto.cart;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CreateCartItemRequestDto {
    private Long bookId;
    @PositiveOrZero
    private int quantity;
}
