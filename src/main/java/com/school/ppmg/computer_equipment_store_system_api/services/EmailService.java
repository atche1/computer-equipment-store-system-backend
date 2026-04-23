package com.school.ppmg.computer_equipment_store_system_api.services;



        import com.school.ppmg.computer_equipment_store_system_api.enums.OrderStatus;
        import com.school.ppmg.computer_equipment_store_system_api.enums.ServiceRequestStatus;
        import com.school.ppmg.computer_equipment_store_system_api.models.Order;
        import com.school.ppmg.computer_equipment_store_system_api.models.ServiceRequest;

public interface EmailService {

    void sendOrderStatusChangedEmail(Order order, OrderStatus oldStatus, OrderStatus newStatus);

    void sendServiceRequestStatusChangedEmail(ServiceRequest serviceRequest,
                                              ServiceRequestStatus oldStatus,
                                              ServiceRequestStatus newStatus);
    void sendOrderCreatedEmail(Order order);

    void sendServiceRequestCreatedEmail(ServiceRequest serviceRequest);
}