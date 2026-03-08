package com.school.ppmg.computer_equipment_store_system_api.dtos.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CheckoutRequest(

        @NotBlank(message = "Delivery name is required")
        @Size(min = 2, max = 120)
        String deliveryName,

        @NotBlank(message = "Phone is required")
        @Pattern(regexp="^\\+?[0-9]{8,15}$", message="Invalid phone number")
        String deliveryPhone,

        @NotBlank(message="Country is required")
        String country,

        @NotBlank(message="City is required")
        String city,

        @NotBlank(message="Postal code is required")
        @Pattern(regexp="^[0-9]{3,10}$", message="Postal code must contain only digits")
        String postalCode,

        @NotBlank(message="Street is required")
        String street,

        @NotBlank(message="Street number is required")
        @Pattern(regexp="^[0-9]+[A-Za-z]?$", message="Invalid street number")
        String streetNumber
) {}