package com.school.ppmg.computer_equipment_store_system_api.repositories;

import com.school.ppmg.computer_equipment_store_system_api.enums.OrderStatus;
import com.school.ppmg.computer_equipment_store_system_api.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNumber(String orderNumber);

    Page<Order> findByUserId(Long userId, Pageable pageable);
    @Query("""
        SELECT o
        FROM Order o
        WHERE (:status IS NULL OR o.status = :status)
          AND (:orderNumber IS NULL OR LOWER(o.orderNumber) LIKE LOWER(CONCAT('%', :orderNumber, '%')))
          AND (:customerName IS NULL OR LOWER(o.deliveryName) LIKE LOWER(CONCAT('%', :customerName, '%')))
          AND (:dateFrom IS NULL OR o.createdAt >= :dateFrom)
          AND (:dateTo IS NULL OR o.createdAt <= :dateTo)
    """)
    Page<Order> searchAdminOrders(
            @Param("status") OrderStatus status,
            @Param("orderNumber") String orderNumber,
            @Param("customerName") String customerName,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo,
            Pageable pageable
    );

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    Page<Order> findByCreatedAtBetween(
            LocalDateTime from, LocalDateTime to, Pageable pageable);
}
