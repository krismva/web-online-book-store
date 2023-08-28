package web.app.onlinebookshop.dto.order;

import lombok.Data;
import web.app.onlinebookshop.model.Status;

@Data
public class UpdateOrderStatusRequestDto {
    private Status status;
}
