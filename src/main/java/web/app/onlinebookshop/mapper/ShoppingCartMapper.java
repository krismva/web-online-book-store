package web.app.onlinebookshop.mapper;

import org.mapstruct.Mapper;
import web.app.onlinebookshop.config.MapperConfig;
import web.app.onlinebookshop.dto.cart.ShoppingCartDto;
import web.app.onlinebookshop.model.ShoppingCart;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

}
