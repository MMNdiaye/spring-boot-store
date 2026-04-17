package com.codewithmosh.store.carts;

import com.codewithmosh.store.products.ProductNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
@Tag(name = "Carts")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(
            UriComponentsBuilder uriBuilder) {
        var cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{cartId}")
                .buildAndExpand(cartDto.getId()).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    @Operation(summary = "Adds a product to the cart")
    public ResponseEntity<CartItemDto> addItemToCart(
            @Valid @RequestBody AddItemToCartRequest request,
            @Parameter(description = "The ID of the cart")
            @PathVariable UUID cartId,
            UriComponentsBuilder uriBuilder) {
        var cartItemDto = cartService.addToCart(cartId, request.getProductId());
        var uri = uriBuilder.path("/carts/{cartId}/items/{productId}")
                .buildAndExpand(cartId, request.getProductId()).toUri();
        return ResponseEntity.created(uri).body(cartItemDto);
    }

    @GetMapping("/{cartId}")
    public CartDto getCart(@PathVariable UUID cartId) {
        var cartDto = cartService.getCart(cartId);
        return cartDto;
    }

    @PutMapping("/{cartId}/items/{productId}")
    public CartItemDto updateItem(
            @PathVariable UUID cartId,
            @PathVariable Long productId,
            @RequestBody @Valid UpdateCartItemRequest request
    ) {
        return cartService.updateItemQuantityInCart(productId, request.getQuantity(), cartId);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteItemFromCart(
            @PathVariable UUID cartId,
            @PathVariable Long productId) {
        cartService.deleteItemFromCart(productId, cartId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> clearCart(@PathVariable UUID cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Void> handleCartNotFound(CartNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Void> handleProductNotFoundInCart(ProductNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }
}
