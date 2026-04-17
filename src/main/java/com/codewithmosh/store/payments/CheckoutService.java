package com.codewithmosh.store.payments;

import com.codewithmosh.store.carts.EmptyCartException;
import com.codewithmosh.store.orders.OrderMapper;
import com.codewithmosh.store.orders.OrderRepository;
import com.codewithmosh.store.auth.AuthService;
import com.codewithmosh.store.carts.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CheckoutService {
    private final AuthService authService;
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final PaymentGateway paymentGateway;


    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) {
        var cartDto = cartService.getCart(request.getCartId());
        var order = orderMapper.toOrder(cartDto);
        var customer = authService.getCurrentUser();
        order.setCustomer(customer);
        if (order.hasNoItems())
            throw new EmptyCartException();

        orderRepository.save(order);
        try {
            var session = paymentGateway.createCheckoutSession(order);

            cartService.clearCart(cartDto.getId());

            return new CheckoutResponse(order.getId(), session.getUrl());
        } catch (PaymentException ex) {
            orderRepository.delete(order);
            throw ex;
        }
    }

    public void handleWebhookEvent(WebhookRequest request) {
        paymentGateway
                .parseWebhookRequest(request)
                .ifPresent(paymentResult -> {
                    var order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow();
                    order.setStatus(paymentResult.getPaymentStatus());
                    orderRepository.save(order);
                });

    }
}
