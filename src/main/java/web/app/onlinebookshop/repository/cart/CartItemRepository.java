package web.app.onlinebookshop.repository.cart;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import web.app.onlinebookshop.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Set<CartItem> findCartItemsByShoppingCartId(Long id);
}
