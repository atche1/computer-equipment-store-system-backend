package com.school.ppmg.computer_equipment_store_system_api.controllers;

import com.school.ppmg.computer_equipment_store_system_api.dtos.cart.CartResponse;
import com.school.ppmg.computer_equipment_store_system_api.dtos.cart.MergeCartRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.cart.UpdateCartItemRequest;
import com.school.ppmg.computer_equipment_store_system_api.services.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public CartResponse getMyCart() {
        return cartService.getMyCart();
    }

    @PostMapping("/merge")
    public CartResponse merge(@Valid @RequestBody MergeCartRequest request) {
        return cartService.merge(request);
    }
    @PutMapping("/items/{itemId}")
    public CartResponse updateItem(@PathVariable Long itemId,
                                   @Valid @RequestBody UpdateCartItemRequest request) {
        return cartService.updateItem(itemId, request);
    }

    @DeleteMapping("/items/{itemId}")
    public CartResponse deleteItem(@PathVariable Long itemId) {
        return cartService.deleteItem(itemId);
    }
}