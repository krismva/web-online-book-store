package web.app.onlinebookshop.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import web.app.onlinebookshop.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
