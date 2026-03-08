package com.school.ppmg.computer_equipment_store_system_api.services;

import com.school.ppmg.computer_equipment_store_system_api.enums.OrderStatus;
import com.school.ppmg.computer_equipment_store_system_api.models.Order;

public interface EmailService {

    void sendOrderStatusChangedEmail(Order order, OrderStatus oldStatus, OrderStatus newStatus);
}