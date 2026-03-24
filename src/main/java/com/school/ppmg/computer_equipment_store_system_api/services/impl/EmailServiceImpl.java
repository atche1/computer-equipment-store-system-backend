package com.school.ppmg.computer_equipment_store_system_api.services.impl;

import com.school.ppmg.computer_equipment_store_system_api.enums.OrderStatus;
import com.school.ppmg.computer_equipment_store_system_api.enums.ServiceRequestStatus;
import com.school.ppmg.computer_equipment_store_system_api.models.Order;
import com.school.ppmg.computer_equipment_store_system_api.models.ServiceRequest;
import com.school.ppmg.computer_equipment_store_system_api.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@org.springframework.stereotype.Service
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
        message.setSubject(buildOrderSubject(newStatus));
        message.setText(buildOrderBody(order, oldStatus, newStatus));

        mailSender.send(message);
    }

    @Override
    public void sendServiceRequestStatusChangedEmail(ServiceRequest serviceRequest,
                                                     ServiceRequestStatus oldStatus,
                                                     ServiceRequestStatus newStatus) {
        if (serviceRequest.getUser() == null
                || serviceRequest.getUser().getEmail() == null
                || serviceRequest.getUser().getEmail().isBlank()) {
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(serviceRequest.getUser().getEmail());
        message.setSubject(buildServiceRequestSubject(newStatus));
        message.setText(buildServiceRequestBody(serviceRequest, oldStatus, newStatus));

        mailSender.send(message);
    }

    private String buildOrderSubject(OrderStatus newStatus) {
        return switch (newStatus) {
            case SHIPPED -> "Your order has been shipped";
            case DELIVERED -> "Your order has been delivered";
            case PROCESSING -> "Your order is being processed";
            case NEW -> "Your order has been placed";
            default -> "Order status updated";
        };
    }

    private String buildOrderBody(Order order, OrderStatus oldStatus, OrderStatus newStatus) {
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

    private String buildServiceRequestSubject(ServiceRequestStatus newStatus) {
        return switch (newStatus) {
            case NEW -> "Your service request has been received";
            case IN_PROGRESS -> "Your service request is in progress";
            case DONE -> "Your service request has been completed";
        };
    }

    private String buildServiceRequestBody(ServiceRequest serviceRequest,
                                           ServiceRequestStatus oldStatus,
                                           ServiceRequestStatus newStatus) {
        String fullName = serviceRequest.getUser().getFirstName() + " " + serviceRequest.getUser().getLastName();

        return """
            Hello %s,

            Your service request has been updated.

            Service: %s
            Previous status: %s
            Current status: %s

            Phone: %s
            Description: %s

            Thank you.
            """.formatted(
                fullName,
                serviceRequest.getService().getName(),
                oldStatus,
                newStatus,
                serviceRequest.getCustomerPhone(),
                serviceRequest.getDescription()
        );
    }
}