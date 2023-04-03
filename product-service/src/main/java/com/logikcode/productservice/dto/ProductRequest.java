package com.logikcode.productservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequest {
    public String name;
    public String description;
    public String price;

}
