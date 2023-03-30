package com.logikcode.orderservice.service;

import com.logikcode.orderservice.dto.OrderLineItemDto;
import com.logikcode.orderservice.dto.OrderRequest;
import com.logikcode.orderservice.model.Order;
import com.logikcode.orderservice.model.OrderLineItem;
import com.logikcode.orderservice.repository.OrderRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class OrderService {
    private final OrderRepositoryJpa repositoryJpa;
    public List<OrderLineItem> placeCustomerOrder(OrderRequest request){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItem> orderLineItems;

        orderLineItems =  request.getOrderLineItemDtoList().
                stream().map(this::mapToOrderLineItem).toList();
        order.setOrderLineItems(orderLineItems);

        repositoryJpa.save(order);

        return orderLineItems;
    }

    private OrderLineItem mapToOrderLineItem(OrderLineItemDto orderLineItemDto){
        OrderLineItem orderItems = new OrderLineItem();
        orderItems.setId(orderItems.getId());
        orderItems.setPrice(orderLineItemDto.getPrice());
        orderItems.setSkuCode(orderLineItemDto.getSkuCode());
        orderItems.setQuantity(orderLineItemDto.getQuantity());
        return orderItems;
    }
}
