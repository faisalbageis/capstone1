package org.example.capston1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {
    @NotEmpty(message = "merchant id can not be empty")
    @Pattern(regexp = "M[0-9]*",message = "merchant id must start with M")
    private String id;

    @NotEmpty(message = "merchant name can not be empty")
    @Size(min = 3,message = "merchant name length must be more than 3")
    private String name;
}
