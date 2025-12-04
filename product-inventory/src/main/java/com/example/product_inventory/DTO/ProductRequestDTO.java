package com.example.product_inventory.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductRequestDTO {
    private String name;
    private String category;
    private Double price;
    private Integer quantity;

}
