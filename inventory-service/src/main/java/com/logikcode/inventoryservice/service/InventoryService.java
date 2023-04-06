package com.logikcode.inventoryservice.service;

import com.logikcode.inventoryservice.dto.InventoryResponse;
import com.logikcode.inventoryservice.model.Inventory;
import com.logikcode.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private Logger logger = LoggerFactory.getLogger(InventoryService.class);

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCodes){
        logger.info(" Resilience4j TimeLimiter 'wait' simulation starts...");
        Thread.sleep(5000L);
        logger.info(" Resilience4j TimeLimiter 'wait' simulation ends");

        List<Inventory>  inventoryList = inventoryRepository.findBySkuCodeIn(skuCodes);
        List<InventoryResponse> inventoryResponses;
            inventoryResponses = inventoryList .stream().map(inventory ->
                  InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .available(inventory.getQuantity() > 0)
                            .build()
                ).collect(Collectors.toList());
        System.out.println("-> INVENTORY RESPONSE "+ inventoryResponses);
        return inventoryResponses;
    }
}
