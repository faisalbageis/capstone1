package org.example.capston1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {
    @NotEmpty(message = "category id can not be empty")
    @Pattern(regexp = "C[0-9]*",message = "category id must start with C")
    private String id;

    @NotEmpty(message = "category name can not be empty")
    @Size(min = 3,message = "category name length must be more than 3")
    private String name;
}
