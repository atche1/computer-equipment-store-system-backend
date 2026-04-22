package com.school.ppmg.computer_equipment_store_system_api.services.impl;

import com.school.ppmg.computer_equipment_store_system_api.dtos.order.CheckoutRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.order.OrderItemResponse;
import com.school.ppmg.computer_equipment_store_system_api.dtos.order.OrderResponse;
import com.school.ppmg.computer_equipment_store_system_api.dtos.order.UpdateOrderStatusRequest;
import com.school.ppmg.computer_equipment_store_system_api.enums.OrderStatus;
import com.school.ppmg.computer_equipment_store_system_api.models.Cart;
import com.school.ppmg.computer_equipment_store_system_api.models.CartItem;
import com.school.ppmg.computer_equipment_store_system_api.models.Order;
import com.school.ppmg.computer_equipment_store_system_api.models.OrderItem;
import com.school.ppmg.computer_equipment_store_system_api.models.Product;
import com.school.ppmg.computer_equipment_store_system_api.models.User;
import com.school.ppmg.computer_equipment_store_system_api.repositories.CartRepository;
import com.school.ppmg.computer_equipment_store_system_api.repositories.OrderRepository;
import com.school.ppmg.computer_equipment_store_system_api.repositories.ProductRepository;
import com.school.ppmg.computer_equipment_store_system_api.repositories.UserRepository;
import com.school.ppmg.computer_equipment_store_system_api.services.EmailService;
import com.school.ppmg.computer_equipment_store_system_api.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.school.ppmg.computer_equipment_store_system_api.models.ProductImage;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    @Value("${app.public-base-url}")
    private String publicBaseUrl;

    @Override
    @Transactional
    public OrderResponse checkout(CheckoutRequest request) {
        User user = getCurrentUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart not found"));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderNumber(generateOrderNumber());
        order.setStatus(OrderStatus.NEW);
        order.setDeliveryName(request.deliveryName());
        order.setDeliveryPhone(request.deliveryPhone());
        String fullDeliveryAddress = String.format(
                "%s, %s, %s %s, %s",
                request.country(),
                request.city(),
                request.street(),
                request.streetNumber(),
                request.postalCode()
        );

        order.setDeliveryAddress(fullDeliveryAddress);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();

            if (product.getQuantity() == null || product.getQuantity() <= 0) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Product is out of stock: " + product.getName()
                );
            }

            int requestedQty = cartItem.getQuantity();
            int availableQty = product.getQuantity();

            if (requestedQty > availableQty) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Not enough stock for product: " + product.getName()
                );
            }

            BigDecimal lineTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(requestedQty));

            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(product);
            oi.setProductNameSnapshot(product.getName());
            oi.setUnitPrice(product.getPrice());
            oi.setQuantity(requestedQty);
            oi.setLineTotal(lineTotal);

            orderItems.add(oi);
            total = total.add(lineTotal);

            product.setQuantity(availableQty - requestedQty);
        }

        order.setItems(orderItems);
        order.setTotalAmount(total);

        Order saved = orderRepository.save(order);

        // clear cart
        cart.getItems().clear();
        cartRepository.save(cart);

        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> getMyOrders(Pageable pageable) {
        User user = getCurrentUser();
        return orderRepository.findByUserId(user.getId(), pageable)
                .map(this::toResponse);
    }
    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        return toResponse(order);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> searchAdminOrders(OrderStatus status,
                                                 String orderNumber,
                                                 String customerName,
                                                 LocalDateTime dateFrom,
                                                 LocalDateTime dateTo,
                                                 Pageable pageable) {

        String normalizedOrderNumber = StringUtils.hasText(orderNumber) ? orderNumber.trim() : null;
        String normalizedCustomerName = StringUtils.hasText(customerName) ? customerName.trim() : null;

        return orderRepository.searchAdminOrders(
                status,
                normalizedOrderNumber,
                normalizedCustomerName,
                dateFrom,
                dateTo,
                pageable
        ).map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getMyOrderById(Long orderId) {
        User user = getCurrentUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This order does not belong to you");
        }

        return toResponse(order);
    }


    @Override
    @Transactional
    public OrderResponse updateStatus(Long orderId, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        OrderStatus oldStatus = order.getStatus();
        OrderStatus newStatus = request.status();

        if (oldStatus == newStatus) {
            return toResponse(order);
        }

        order.setStatus(newStatus);
        Order saved = orderRepository.save(order);

        try {
            emailService.sendOrderStatusChangedEmail(saved, oldStatus, newStatus);
        } catch (Exception ignored) {
            // По желание може да логваш, но да не чупиш update-а
        }

        return toResponse(saved);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    private OrderResponse toResponse(Order order) {
        List<OrderItemResponse> items = order.getItems() == null
                ? List.of()
                : order.getItems().stream().map(oi -> new OrderItemResponse(
                oi.getId(),
                oi.getProduct().getId(),
                oi.getProductNameSnapshot(),
                resolveProductImageUrl(oi.getProduct()),
                oi.getUnitPrice(),
                oi.getQuantity(),
                oi.getLineTotal()
        )).toList();

        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getDeliveryName(),
                order.getDeliveryPhone(),
                order.getDeliveryAddress(),
                order.getCreatedAt(),
                items
        );
    }
    private String generateOrderNumber() {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int rnd = ThreadLocalRandom.current().nextInt(1000, 9999);
        return "ORD-" + date + "-" + rnd;
    }
    private String resolveProductImageUrl(Product product) {
        if (product == null || product.getImages() == null || product.getImages().isEmpty()) {
            return null;
        }

        String rawUrl = product.getImages().stream()
                .filter(img -> Boolean.TRUE.equals(img.getIsMain()))
                .map(ProductImage::getImageUrl)
                .findFirst()
                .orElseGet(() -> product.getImages().stream()
                        .map(ProductImage::getImageUrl)
                        .findFirst()
                        .orElse(null));

        return resolveImageUrl(rawUrl);
    }

    private String resolveImageUrl(String imageUrl) {
        if (!StringUtils.hasText(imageUrl)) {
            return imageUrl;
        }

        if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
            return imageUrl;
        }

        if (imageUrl.startsWith("/")) {
            return publicBaseUrl + imageUrl;
        }

        return publicBaseUrl + "/" + imageUrl;
    }
}