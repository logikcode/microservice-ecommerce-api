package com.logikcode.orderservice.controller;

import com.logikcode.orderservice.dto.OrderRequest;
import com.logikcode.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/order/create")
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "timeLimiterApi", fallbackMethod = "")
    @Retry(name = "retryApi")
    /* this indicates that the circuit breaker will be applied to this endpoint which calls inventory service and if failed, will call the fallback method */
    public CompletableFuture<String> handleOrderPlacing(@RequestBody OrderRequest orderRequest){
        orderService.placeCustomerOrder(orderRequest);
        return CompletableFuture.supplyAsync(()-> orderService.placeCustomerOrder(orderRequest));
    }

    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException exception){

        return CompletableFuture.supplyAsync(()->"Call to the inventory service failed");

    }
}
