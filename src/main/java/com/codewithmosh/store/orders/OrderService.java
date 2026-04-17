package com.codewithmosh.store.orders;

import com.codewithmosh.store.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderService {
    private OrderMapper orderMapper;
    private OrderRepository orderRepository;
    private AuthService authService;



    public Set<OrderDto> getAllOrders() {
        var user = authService.getCurrentUser();
        return orderRepository.getOrdersByCustomer(user)
                .stream().map(orderMapper::toDto)
                .collect(Collectors.toSet());
    }

    public OrderDto getOrder(Long orderId) {
        var user = authService.getCurrentUser();
        var order = orderRepository.getOrderWithItems(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (!order.isPlacedBy(user))
            throw new AccessDeniedException("You don't have access to this order");

        return orderMapper.toDto(order);
    }
}
