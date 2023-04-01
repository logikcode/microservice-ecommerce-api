package com.logikcode.inventoryservice.repository;

import com.logikcode.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findBySkuCode(String sku);

    List<Inventory> findBySkuCodeIn(List<String> skuCodes);
}
