package com.logikcode.productservice.service;

import com.logikcode.productservice.dto.ProductRequest;
import com.logikcode.productservice.dto.ProductResponse;
import com.logikcode.productservice.model.Product;
import com.logikcode.productservice.repositoryjpa.ProductRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepositoryJpa productRepository;
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    public void handleProductCreation(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

       Product savedProduct =  productRepository.save(product);
        logger.info("Product with id: {} is saved", savedProduct.getId());

    }

    public List<ProductResponse> fetchAllProducts() {
        List<ProductResponse> productResponses;
       List<Product> products = productRepository.findAll();

      productResponses = products.stream().map(this::mapToProductResponse).collect(Collectors.toList());
      return productResponses;
    }

    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
        return productResponse;

    }
}
