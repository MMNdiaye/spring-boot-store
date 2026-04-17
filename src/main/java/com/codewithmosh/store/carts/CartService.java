package com.codewithmosh.store.carts;

import com.codewithmosh.store.products.ProductNotFoundException;
import com.codewithmosh.store.products.ProductNotFoundInCartException;
import com.codewithmosh.store.products.ProductRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public CartDto createCart() {
        var cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartItemDto addToCart(UUID cartId, Long productId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null)
            throw new CartNotFoundException(cartId);

        var product = productRepository.findById(productId).orElse(null);
        if (product == null)
            throw new ProductNotFoundInCartException(productId, cartId);

        var cartItem = cart.addItem(product);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public CartDto getCart(UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null)
            throw new CartNotFoundException(cartId);

        return cartMapper.toDto(cart);
    }

    public CartItemDto updateItemQuantityInCart(Long productId, Integer quantity, UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null)
            throw new CartNotFoundException(cartId);

        var cartItem = cart.getItem(productId);
        if (cartItem == null)
            throw new ProductNotFoundException(productId, cartId);

        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public void deleteItemFromCart(Long productId, UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null)
            return;
        cart.removeItem(productId);
        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null)
            throw new CartNotFoundException(cartId);
        cart.clear();
        cartRepository.save(cart);
    }
}
