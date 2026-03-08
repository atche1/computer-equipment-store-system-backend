package com.school.ppmg.computer_equipment_store_system_api.services.impl;

import com.school.ppmg.computer_equipment_store_system_api.dtos.cart.CartItemResponse;
import com.school.ppmg.computer_equipment_store_system_api.dtos.cart.CartResponse;
import com.school.ppmg.computer_equipment_store_system_api.dtos.cart.MergeCartRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.cart.UpdateCartItemRequest;
import com.school.ppmg.computer_equipment_store_system_api.models.Cart;
import com.school.ppmg.computer_equipment_store_system_api.models.CartItem;
import com.school.ppmg.computer_equipment_store_system_api.models.Product;
import com.school.ppmg.computer_equipment_store_system_api.models.User;
import com.school.ppmg.computer_equipment_store_system_api.repositories.CartItemRepository;
import com.school.ppmg.computer_equipment_store_system_api.repositories.CartRepository;
import com.school.ppmg.computer_equipment_store_system_api.repositories.ProductRepository;
import com.school.ppmg.computer_equipment_store_system_api.repositories.UserRepository;
import com.school.ppmg.computer_equipment_store_system_api.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public CartResponse getMyCart() {
        Cart cart = getOrCreateCart(getCurrentUser());
        cart.getItems().size(); // initialize
        return toResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse merge(MergeCartRequest request) {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);

        // Намираме всички продукти с един query
        Set<Long> productIds = request.items().keySet();
        Map<Long, Product> products = productRepository.findAllById(productIds)
                .stream().collect(Collectors.toMap(Product::getId, p -> p));

        // existing items by productId
        Map<Long, CartItem> existingByProductId = cart.getItems()
                .stream()
                .collect(Collectors.toMap(ci -> ci.getProduct().getId(), ci -> ci));

        for (Map.Entry<Long, Integer> e : request.items().entrySet()) {
            Long productId = e.getKey();
            Integer qtyToAdd = e.getValue();

            Product product = products.get(productId);
            if (product == null) {
                // продуктът липсва -> skip
                continue;
            }

            int available = safe(product.getQuantity()); // ако при теб е друго поле - смени тук
            if (available <= 0) {
                // няма наличност -> skip
                continue;
            }

            CartItem existing = existingByProductId.get(productId);
            if (existing == null) {
                int finalQty = Math.min(qtyToAdd, available);
                if (finalQty <= 0) continue;

                CartItem newItem = new CartItem();
                newItem.setCart(cart);
                newItem.setProduct(product);
                newItem.setQuantity(finalQty);

                cart.getItems().add(newItem);
            } else {
                int mergedQty = safe(existing.getQuantity()) + qtyToAdd;
                int finalQty = Math.min(mergedQty, available);

                if (finalQty <= 0) {
                    cart.getItems().remove(existing);
                    cartItemRepository.delete(existing);
                } else {
                    existing.setQuantity(finalQty);
                }
            }
        }

        Cart saved = cartRepository.save(cart);
        saved.getItems().size();
        return toResponse(saved);
    }

    // ----------------- helpers -----------------

    private Cart getOrCreateCart(User user) {
        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setUser(user);
                    c.setItems(new ArrayList<>());
                    return cartRepository.save(c);
                });
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        // auth.getName() обикновено е email/username
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    private CartResponse toResponse(Cart cart) {
        List<CartItemResponse> items = cart.getItems().stream().map(ci -> {
            Product p = ci.getProduct();
            int available = safe(p.getQuantity());
            return new CartItemResponse(
                    ci.getId(),
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    safe(ci.getQuantity()),
                    available
            );
        }).toList();

        BigDecimal total = items.stream()
                .map(i -> i.unitPrice().multiply(BigDecimal.valueOf(i.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(cart.getId(), items, total);
    }
    @Override
    @Transactional
    public CartResponse updateItem(Long itemId, UpdateCartItemRequest request) {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found"));

        // security: item must belong to current user
        if (!item.getCart().getId().equals(cart.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Item does not belong to your cart");
        }

        int qty = request.quantity() == null ? 0 : request.quantity();
        if (qty <= 0) {
            cart.getItems().remove(item);
            cartItemRepository.delete(item);
            Cart saved = cartRepository.save(cart);
            saved.getItems().size();
            return toResponse(saved);
        }

        Product p = item.getProduct();
        int available = safe(p.getQuantity());
        int finalQty = Math.min(qty, available);

        if (finalQty <= 0) {
            cart.getItems().remove(item);
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(finalQty);
        }

        Cart saved = cartRepository.save(cart);
        saved.getItems().size();
        return toResponse(saved);
    }

    @Override
    @Transactional
    public CartResponse deleteItem(Long itemId) {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found"));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Item does not belong to your cart");
        }

        cart.getItems().remove(item);
        cartItemRepository.delete(item);

        Cart saved = cartRepository.save(cart);
        saved.getItems().size();
        return toResponse(saved);
    }

    private int safe(Integer v) {
        return v == null ? 0 : v;
    }
}