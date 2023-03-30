package com.logikcode.productservice.controller;

import com.logikcode.productservice.dto.ProductRequest;
import com.logikcode.productservice.dto.ProductResponse;
import com.logikcode.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest product){
        productService.handleProductCreation(product);
    }

    @RequestMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> handleProductsRetrieval(){
        return productService.fetchAllProducts();
    }
}
