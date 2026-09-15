package org.example.capston1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {
    @NotEmpty(message = "merchantStock can not be empty")
    @Pattern(regexp = "MS[0-9]*",message = "merchantStock id must start with MS")
    private String id;

    @NotEmpty(message = "merchantStock must have product id ")
    private String productId;

    @NotEmpty(message = "merchantStock must have merchant id ")
    private String merchantId;

    @NotNull(message = "stock can not be empty")
    @Min(value = 10,message = "have to be more than 10 ")
    private int stock;


}
