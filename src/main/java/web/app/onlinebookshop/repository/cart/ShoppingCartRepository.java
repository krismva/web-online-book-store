package web.app.onlinebookshop.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import web.app.onlinebookshop.model.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart getShoppingCartByUserId(Long id);
}
