package com.logikcode.inventoryservice;

import com.logikcode.inventoryservice.model.Inventory;
import com.logikcode.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class InventoryServiceApplication {
	private final InventoryRepository inventoryRepository;
	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);


	}

	@Bean
	public CommandLineRunner loadInventory(){
	 return 	args -> {
		 Inventory iphone13 = new Inventory();
		 iphone13.setSkuCode("iPhone_13");
		 iphone13.setQuantity(20);

		 Inventory iphone14 = new Inventory();
				 iphone14.setSkuCode("iPhone_14");
				 iphone14.setQuantity(15);

				 inventoryRepository.save(iphone13);
				 inventoryRepository.save(iphone14);
		};
	}
}
