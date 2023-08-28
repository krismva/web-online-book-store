package web.app.onlinebookshop.dto.order;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderItemRequestDto {
    private Long orderId;
    private Long bookId;
    private int quantity;
    private BigDecimal price;
}
