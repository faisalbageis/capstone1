package org.example.capston1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    @NotEmpty(message = "product id can not be empty")
    @Pattern(regexp = "P[0-9]*",message = "product id must start with P")
    private String id;

    @NotEmpty(message = "product name can not be empty")
    @Size(min = 3,message = "name length must be more than 3")
    private String name;

    @NotNull(message = "product must have price")
    @Positive(message = "price can not be negative ")
    private double price;

    @NotEmpty(message = "product must have category id")
    private String categoryId;

}
