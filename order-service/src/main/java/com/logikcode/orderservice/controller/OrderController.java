package com.logikcode.orderservice.controller;

import com.logikcode.orderservice.dto.OrderRequest;
import com.logikcode.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/order/create")
    public String handleOrderPlacing(@RequestBody OrderRequest orderRequest){
        orderService.placeCustomerOrder(orderRequest);
        return "Order successfully placed";
    }
}
