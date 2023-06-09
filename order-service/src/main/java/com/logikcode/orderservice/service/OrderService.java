package com.logikcode.orderservice.service;

import com.logikcode.orderservice.dto.InventoryResponse;
import com.logikcode.orderservice.dto.OrderLineItemDto;
import com.logikcode.orderservice.dto.OrderRequest;
import com.logikcode.orderservice.event.OrderPlaceEvent;
import com.logikcode.orderservice.model.Order;
import com.logikcode.orderservice.model.OrderLineItem;
import com.logikcode.orderservice.repository.OrderRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepositoryJpa repositoryJpa;
    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;
    private final KafkaTemplate<String, OrderPlaceEvent> kafkaTemplate;
    public String placeCustomerOrder(OrderRequest request){
        final String SUCCESS_MESSAGE = "Order successfully placed";
        final String UNSUCCESSFUL_MESSAGE = "Order failed";
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<String> skuCodes;
           skuCodes = request.getOrderLineItemDtoList().stream()
                .map(orderLineItemDto -> orderLineItemDto.getSkuCode())
                .collect(Collectors.toList());

        System.out.println("-> SKU CODES "+ skuCodes);
        List<OrderLineItem> orderLineItems;

        orderLineItems =  request.getOrderLineItemDtoList().
                stream().map(this::mapToOrderLineItem).collect(Collectors.toList());

        order.setOrderLineItems(orderLineItems);

    /*+ manual creation of span for distributed tracing due to fact that this code bloce executes in another thread
        * and service call to 'inventory service would not be picked up by default by zipkin
    */
        Span inventoryLookup = tracer.nextSpan().name("InventoryServiceLookup");
        try(Tracer.SpanInScope spanInScope = tracer.withSpan(inventoryLookup.start())){
            //        api call to inventory service
            InventoryResponse[] result = webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory/check",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

//        inventory-service/api/inventory/check
            assert result != null;
            boolean allOrderInStock = Arrays.stream(result)
                    .allMatch(inventoryResponse -> inventoryResponse.getAvailable());
            if (allOrderInStock){
                repositoryJpa.save(order);
                kafkaTemplate.send("notificationTopic", new OrderPlaceEvent(order.getOrderNumber()));
                return SUCCESS_MESSAGE;
            }else {
                throw new IllegalArgumentException("Product is not in stock");
            }

        } finally {
            inventoryLookup.end();
        }

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
