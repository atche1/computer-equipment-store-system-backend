package com.school.ppmg.computer_equipment_store_system_api.services;

import com.school.ppmg.computer_equipment_store_system_api.dtos.cart.CartResponse;
import com.school.ppmg.computer_equipment_store_system_api.dtos.cart.MergeCartRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.cart.UpdateCartItemRequest;

public interface CartService {
    CartResponse getMyCart();
    CartResponse merge(MergeCartRequest request);
    CartResponse updateItem(Long itemId, UpdateCartItemRequest request);
    CartResponse deleteItem(Long itemId);
}