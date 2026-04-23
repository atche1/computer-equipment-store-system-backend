package com.school.ppmg.computer_equipment_store_system_api.services;

import com.school.ppmg.computer_equipment_store_system_api.dtos.order.CheckoutRequest;
import com.school.ppmg.computer_equipment_store_system_api.enums.OrderStatus;
import com.school.ppmg.computer_equipment_store_system_api.enums.Role;
import com.school.ppmg.computer_equipment_store_system_api.models.*;
import com.school.ppmg.computer_equipment_store_system_api.repositories.CartRepository;
import com.school.ppmg.computer_equipment_store_system_api.repositories.OrderRepository;
import com.school.ppmg.computer_equipment_store_system_api.repositories.ProductRepository;
import com.school.ppmg.computer_equipment_store_system_api.repositories.UserRepository;
import com.school.ppmg.computer_equipment_store_system_api.services.EmailService;
import com.school.ppmg.computer_equipment_store_system_api.services.impl.OrderServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private OrderRepository orderRepository;
    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private EmailService emailService;
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderRepository = Mockito.mock(OrderRepository.class);
        cartRepository = Mockito.mock(CartRepository.class);
        productRepository = Mockito.mock(ProductRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        emailService = Mockito.mock(EmailService.class);

        orderService = new OrderServiceImpl(
                orderRepository,
                cartRepository,
                productRepository,
                userRepository,
                emailService
        );

        ReflectionTestUtils.setField(orderService, "publicBaseUrl", "http://localhost:8080");

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("customer@example.com", null)
        );
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void checkout_shouldCreateOrderAndDecreaseStock() {
        User user = User.builder()
                .id(1L)
                .email("customer@example.com")
                .passwordHash("encoded")
                .firstName("Ivan")
                .lastName("Petrov")
                .phone("+359888123456")
                .role(Role.CUSTOMER)
                .enabled(true)
                .build();

        Category category = Category.builder()
                .id(1L)
                .name("Laptops")
                .slug("laptops")
                .isActive(true)
                .build();

        Product product = Product.builder()
                .id(10L)
                .name("Dell XPS")
                .price(new BigDecimal("2000.00"))
                .quantity(5)
                .isActive(true)
                .category(category)
                .images(new ArrayList<>())
                .build();

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);

        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(2);

        cart.setItems(new ArrayList<>(List.of(cartItem)));

        when(userRepository.findByEmail("customer@example.com")).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

        CheckoutRequest request = new CheckoutRequest(
                "Ivan Petrov",
                "+359888123456",
                "Bulgaria",
                "Sofia",
                "1000",
                "Vitosha Blvd",
                "15"
        );

        var response = orderService.checkout(request);

        assertEquals(OrderStatus.NEW, response.status());
        assertEquals(new BigDecimal("4000.00"), response.totalAmount());
        assertEquals(3, product.getQuantity());
        assertTrue(cart.getItems().isEmpty());

        verify(orderRepository).save(any(Order.class));
        verify(cartRepository).save(cart);
    }

    @Test
    void checkout_shouldThrow_whenStockIsNotEnough() {
        User user = User.builder()
                .id(1L)
                .email("customer@example.com")
                .passwordHash("encoded")
                .firstName("Ivan")
                .lastName("Petrov")
                .phone("+359888123456")
                .role(Role.CUSTOMER)
                .enabled(true)
                .build();

        Category category = Category.builder()
                .id(1L)
                .name("Laptops")
                .slug("laptops")
                .isActive(true)
                .build();

        Product product = Product.builder()
                .id(10L)
                .name("Dell XPS")
                .price(new BigDecimal("2000.00"))
                .quantity(1)
                .isActive(true)
                .category(category)
                .images(new ArrayList<>())
                .build();

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);

        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(2);

        cart.setItems(new ArrayList<>(List.of(cartItem)));

        when(userRepository.findByEmail("customer@example.com")).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));

        CheckoutRequest request = new CheckoutRequest(
                "Ivan Petrov",
                "+359888123456",
                "Bulgaria",
                "Sofia",
                "1000",
                "Vitosha Blvd",
                "15"
        );

        assertThrows(ResponseStatusException.class, () -> orderService.checkout(request));

        verify(orderRepository, never()).save(any());
    }
}