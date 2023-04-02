package com.logikcode.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItemDto {
    private String id;
    private String skuCode;
    private String price;
    private String quantity;
}
