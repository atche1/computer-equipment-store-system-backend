package com.school.ppmg.computer_equipment_store_system_api.services.impl;

import com.school.ppmg.computer_equipment_store_system_api.enums.OrderStatus;
import com.school.ppmg.computer_equipment_store_system_api.models.Order;
import com.school.ppmg.computer_equipment_store_system_api.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String from;

    @Override
    public void sendOrderStatusChangedEmail(Order order, OrderStatus oldStatus, OrderStatus newStatus) {
        if (order.getUser() == null || order.getUser().getEmail() == null || order.getUser().getEmail().isBlank()) {
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(order.getUser().getEmail());
        message.setSubject(buildSubject(order, newStatus));
        message.setText(buildBody(order, oldStatus, newStatus));

        mailSender.send(message);
    }

    private String buildSubject(Order order, OrderStatus newStatus) {
        return switch (newStatus) {
            case SHIPPED -> "Your order has been shipped";
            case DELIVERED -> "Your order has been delivered";
            case PROCESSING -> "Your order is being processed";
            case NEW -> "Your order has been placed";
            default -> "Order status updated";
        };
    }

    private String buildBody(Order order, OrderStatus oldStatus, OrderStatus newStatus) {
        return """
                Hello %s,

                Your order %s status has been updated.

                Previous status: %s
                Current status: %s

                Delivery name: %s
                Delivery phone: %s
                Delivery address: %s

                Total amount: %s

                Thank you for shopping with us.
                """.formatted(
                order.getDeliveryName(),
                order.getOrderNumber(),
                oldStatus,
                newStatus,
                order.getDeliveryName(),
                order.getDeliveryPhone(),
                order.getDeliveryAddress(),
                order.getTotalAmount()
        );
    }
}