package web.app.onlinebookshop.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import web.app.onlinebookshop.config.MapperConfig;
import web.app.onlinebookshop.dto.cart.CartItemDto;
import web.app.onlinebookshop.dto.cart.CreateCartItemRequestDto;
import web.app.onlinebookshop.dto.cart.UpdateQuantityDto;
import web.app.onlinebookshop.model.CartItem;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(target = "shoppingCartId", ignore = true)
    CartItemDto toDto(CartItem cartItem);

    @Mapping(target = "id", ignore = true)
    CartItem toModel(CreateCartItemRequestDto requestDto);

    UpdateQuantityDto updateToDto(CartItem cartItem);

    @AfterMapping
    default void setShoppingCartId(@MappingTarget CartItemDto cartDto, CartItem cartItem) {
        cartDto.setShoppingCartId(cartItem.getShoppingCart().getId());
    }

    @AfterMapping
    default void setBookId(@MappingTarget CartItemDto cartDto, CartItem cartItem) {
        cartDto.setBookId(cartItem.getBook().getId());
    }

    @AfterMapping
    default void setBookTitle(@MappingTarget CartItemDto cartDto, CartItem cartItem) {
        cartDto.setBookTitle(cartItem.getBook().getTitle());
    }
}
