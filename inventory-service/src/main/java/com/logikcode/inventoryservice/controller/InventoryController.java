package com.logikcode.inventoryservice.controller;

import com.logikcode.inventoryservice.dto.InventoryResponse;
import com.logikcode.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class InventoryController {
    private final InventoryService inventoryService;
    @GetMapping("/inventory/check")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isItemInStock(@RequestParam("skuCode") List<String> skuCodes){
        System.out.println("END POINT HIT "+ skuCodes);
       return inventoryService.isInStock(skuCodes);
    }
}
