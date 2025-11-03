package com.rhayven.midterm.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductDTO(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Description is required")
        String description,

        @Min(value = 1, message = "Stock must be at least 1")
        int stock,

        @NotBlank(message = "Unit is required")
        String unit,

        @NotNull(message = "Price is required")
        @Min(value = 1, message = "Price must be at least 1")
        double price
) {}