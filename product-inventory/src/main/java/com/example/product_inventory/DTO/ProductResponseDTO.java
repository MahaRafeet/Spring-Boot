package com.example.product_inventory.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductResponseDTO {
    private Long id;
    private String name;
    private String category;
    private Double price;
    private Integer quantity;
    private Boolean active=true;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
