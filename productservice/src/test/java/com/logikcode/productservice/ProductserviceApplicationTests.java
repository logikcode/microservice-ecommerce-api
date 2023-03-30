package com.logikcode.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logikcode.productservice.dto.ProductRequest;
import com.logikcode.productservice.repositoryjpa.ProductRepositoryJpa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductserviceApplicationTests {
	@Container // to tell junit 5 that this is a test container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	ObjectMapper mapper;
	private ProductRepositoryJpa productRepository;
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);

	}
	@Test
	void shouldCreateProduct() throws Exception {
		 ProductRequest product =   getProductRequest();
		String convertedProduct = mapper.writeValueAsString(product);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(convertedProduct)
		)
				.andExpect(status().isCreated());

		Assertions.assertEquals(productRepository.findAll().size(), 1);
	}

	private ProductRequest getProductRequest() {
		ProductRequest productRequest = new ProductRequest();
		productRequest.setName("iPhone 14");
		productRequest.setDescription("iPhone 14 device");
		productRequest.setPrice("100k");
		return productRequest;
	}

}
