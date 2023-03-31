package com.logikcode.inventoryservice.service;

import com.logikcode.inventoryservice.dto.InventoryResponse;
import com.logikcode.inventoryservice.model.Inventory;
import com.logikcode.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCodes){
        List<Inventory>  inventoryList = inventoryRepository.findBySkuCodeIn(skuCodes);
            return inventoryList .stream().map(inventory ->
                  InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .available(inventory.getQuantity() > 0)
                            .build()
                ).collect(Collectors.toList());
    }
}
