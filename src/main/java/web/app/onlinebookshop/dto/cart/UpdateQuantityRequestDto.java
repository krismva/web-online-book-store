package web.app.onlinebookshop.dto.cart;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateQuantityRequestDto {
    @PositiveOrZero
    private int quantity;
}
