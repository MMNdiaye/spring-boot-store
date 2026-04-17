package com.codewithmosh.store.orders;

import com.codewithmosh.store.carts.CartDto;
import com.codewithmosh.store.carts.CartMapper;
import com.codewithmosh.store.carts.CartItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CartMapper.class})
public interface OrderMapper {
    OrderDto toDto(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "unitPrice", expression = "java(cartItem.getProduct().getPrice())")
    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    OrderItem toOrderItem(CartItem cartItem);

    OrderItemDto toDto(OrderItem orderItem);
    @AfterMapping
    default void overridePrice(
            OrderItem orderItem, @MappingTarget OrderItemDto dto) {
        if (dto.getProduct() != null)
            dto.getProduct().setPrice(orderItem.getUnitPrice());
    }

    @Mapping(target = "id", ignore = true)
    Order toOrder(CartDto cartDto);
}
