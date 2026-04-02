package com.school.ppmg.computer_equipment_store_system_api.services.impl;

import com.school.ppmg.computer_equipment_store_system_api.enums.OrderStatus;
import com.school.ppmg.computer_equipment_store_system_api.enums.ServiceRequestStatus;
import com.school.ppmg.computer_equipment_store_system_api.models.Order;
import com.school.ppmg.computer_equipment_store_system_api.models.ServiceRequest;
import com.school.ppmg.computer_equipment_store_system_api.services.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String from;

    @Value("${app.store-name:EchtaTech}")
    private String storeName;

    @Value("${app.store-url:http://localhost:8081}")
    private String storeUrl;

    @Override
    public void sendOrderStatusChangedEmail(Order order, OrderStatus oldStatus, OrderStatus newStatus) {
        if (order.getUser() == null || order.getUser().getEmail() == null || order.getUser().getEmail().isBlank()) {
            return;
        }

        String subject = buildOrderSubject(newStatus);
        String html = buildOrderHtml(order, oldStatus, newStatus);

        sendHtmlEmail(order.getUser().getEmail(), subject, html);
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

        String subject = buildServiceRequestSubject(newStatus);
        String html = buildServiceRequestHtml(serviceRequest, oldStatus, newStatus);

        sendHtmlEmail(serviceRequest.getUser().getEmail(), subject, html);
    }

    private void sendHtmlEmail(String to, String subject, String html) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage,
                    false,
                    StandardCharsets.UTF_8.name()
            );

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
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

    private String buildServiceRequestSubject(ServiceRequestStatus newStatus) {
        return switch (newStatus) {
            case NEW -> "Your service request has been received";
            case IN_PROGRESS -> "Your service request is in progress";
            case DONE -> "Your service request has been completed";
        };
    }

    private String buildOrderHtml(Order order, OrderStatus oldStatus, OrderStatus newStatus) {
        String customerName = safe(order.getDeliveryName());

        String content = """
            <h2 style="margin:0 0 16px 0;color:#0d4dad;">Order update</h2>
            <p style="margin:0 0 16px 0;font-size:15px;color:#374151;">
                Hello <strong>%s</strong>,
            </p>
            <p style="margin:0 0 20px 0;font-size:15px;color:#374151;">
                The status of your order has been updated.
            </p>

            <table style="width:100%%;border-collapse:collapse;margin-bottom:20px;">
                <tr>
                    <td style="padding:10px;border:1px solid #e5e7eb;background:#f9fafb;"><strong>Order number</strong></td>
                    <td style="padding:10px;border:1px solid #e5e7eb;">%s</td>
                </tr>
                <tr>
                    <td style="padding:10px;border:1px solid #e5e7eb;background:#f9fafb;"><strong>Previous status</strong></td>
                    <td style="padding:10px;border:1px solid #e5e7eb;">%s</td>
                </tr>
                <tr>
                    <td style="padding:10px;border:1px solid #e5e7eb;background:#f9fafb;"><strong>Current status</strong></td>
                    <td style="padding:10px;border:1px solid #e5e7eb;">%s</td>
                </tr>
                <tr>
                    <td style="padding:10px;border:1px solid #e5e7eb;background:#f9fafb;"><strong>Delivery name</strong></td>
                    <td style="padding:10px;border:1px solid #e5e7eb;">%s</td>
                </tr>
                <tr>
                    <td style="padding:10px;border:1px solid #e5e7eb;background:#f9fafb;"><strong>Delivery phone</strong></td>
                    <td style="padding:10px;border:1px solid #e5e7eb;">%s</td>
                </tr>
                <tr>
                    <td style="padding:10px;border:1px solid #e5e7eb;background:#f9fafb;"><strong>Delivery address</strong></td>
                    <td style="padding:10px;border:1px solid #e5e7eb;">%s</td>
                </tr>
                <tr>
                    <td style="padding:10px;border:1px solid #e5e7eb;background:#f9fafb;"><strong>Total amount</strong></td>
                    <td style="padding:10px;border:1px solid #e5e7eb;">%s</td>
                </tr>
            </table>

            <div style="margin-top:24px;">
                <a href="%s"
                   style="display:inline-block;padding:12px 22px;background:#0d4dad;color:#ffffff;text-decoration:none;border-radius:8px;font-weight:600;">
                    Visit our store
                </a>
            </div>
            """.formatted(
                escapeHtml(customerName),
                escapeHtml(safe(order.getOrderNumber())),
                escapeHtml(String.valueOf(oldStatus)),
                escapeHtml(String.valueOf(newStatus)),
                escapeHtml(safe(order.getDeliveryName())),
                escapeHtml(safe(order.getDeliveryPhone())),
                escapeHtml(safe(order.getDeliveryAddress())),
                escapeHtml(String.valueOf(order.getTotalAmount())),
                storeUrl
        );

        return wrapEmailLayout("Order status changed", content);
    }

    private String buildServiceRequestHtml(ServiceRequest serviceRequest,
                                           ServiceRequestStatus oldStatus,
                                           ServiceRequestStatus newStatus) {

        String fullName = safe(serviceRequest.getUser().getFirstName()) + " " + safe(serviceRequest.getUser().getLastName());

        String content = """
            <h2 style="margin:0 0 16px 0;color:#0d4dad;">Service request update</h2>
            <p style="margin:0 0 16px 0;font-size:15px;color:#374151;">
                Hello <strong>%s</strong>,
            </p>
            <p style="margin:0 0 20px 0;font-size:15px;color:#374151;">
                Your service request status has been updated.
            </p>

            <table style="width:100%%;border-collapse:collapse;margin-bottom:20px;">
                <tr>
                    <td style="padding:10px;border:1px solid #e5e7eb;background:#f9fafb;"><strong>Service</strong></td>
                    <td style="padding:10px;border:1px solid #e5e7eb;">%s</td>
                </tr>
                <tr>
                    <td style="padding:10px;border:1px solid #e5e7eb;background:#f9fafb;"><strong>Previous status</strong></td>
                    <td style="padding:10px;border:1px solid #e5e7eb;">%s</td>
                </tr>
                <tr>
                    <td style="padding:10px;border:1px solid #e5e7eb;background:#f9fafb;"><strong>Current status</strong></td>
                    <td style="padding:10px;border:1px solid #e5e7eb;">%s</td>
                </tr>
                <tr>
                    <td style="padding:10px;border:1px solid #e5e7eb;background:#f9fafb;"><strong>Phone</strong></td>
                    <td style="padding:10px;border:1px solid #e5e7eb;">%s</td>
                </tr>
                <tr>
                    <td style="padding:10px;border:1px solid #e5e7eb;background:#f9fafb;"><strong>Description</strong></td>
                    <td style="padding:10px;border:1px solid #e5e7eb;">%s</td>
                </tr>
            </table>

            <div style="margin-top:24px;">
                <a href="%s"
                   style="display:inline-block;padding:12px 22px;background:#0d4dad;color:#ffffff;text-decoration:none;border-radius:8px;font-weight:600;">
                    Open %s
                </a>
            </div>
            """.formatted(
                escapeHtml(fullName.trim()),
                escapeHtml(safe(serviceRequest.getService().getName())),
                escapeHtml(String.valueOf(oldStatus)),
                escapeHtml(String.valueOf(newStatus)),
                escapeHtml(safe(serviceRequest.getCustomerPhone())),
                escapeHtml(safe(serviceRequest.getDescription())),
                storeUrl,
                escapeHtml(storeName)
        );

        return wrapEmailLayout("Service request updated", content);
    }

    private String wrapEmailLayout(String title, String content) {
        return """
    <!doctype html>
    <html lang="en">
    <body style="margin:0;padding:0;background:#f3f6fb;font-family:Arial,Helvetica,sans-serif;">
        <div style="padding:32px 16px;">
            <table role="presentation"
                   style="width:100%%;max-width:680px;margin:0 auto;border-collapse:collapse;background:#ffffff;border-radius:22px;overflow:hidden;box-shadow:0 10px 30px rgba(15,23,42,.08);">
                
                <tr>
                    <td style="background:linear-gradient(135deg,#0d4dad,#2563eb);padding:34px 24px 30px;text-align:center;">
                        
                        <div style="display:inline-block;background:#ffffff;color:#0d4dad;padding:12px 22px;border-radius:18px;box-shadow:0 8px 24px rgba(0,0,0,.12);margin-bottom:20px;font-size:28px;font-weight:800;letter-spacing:.3px;">
                            %s
                        </div>

                        <div style="font-size:16px;color:rgba(255,255,255,.92);margin-top:10px;">
                            %s
                        </div>
                    </td>
                </tr>

                <tr>
                    <td style="padding:34px 32px;">
                        %s
                    </td>
                </tr>

                <tr>
                    <td style="padding:18px 32px;background:#f9fafb;color:#6b7280;font-size:13px;text-align:center;border-top:1px solid #e5e7eb;">
                        This is an automatic email from %s.
                    </td>
                </tr>
            </table>
        </div>
    </body>
    </html>
    """.formatted(
                escapeHtml(storeName),
                escapeHtml(title),
                content,
                escapeHtml(storeName)
        );
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    private String escapeHtml(String value) {
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}