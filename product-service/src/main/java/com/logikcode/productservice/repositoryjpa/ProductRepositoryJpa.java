package com.logikcode.productservice.repositoryjpa;

import com.logikcode.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepositoryJpa extends MongoRepository<Product, String> {

}
