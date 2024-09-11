package com.pitara.product.repository;

import com.pitara.product.entity.ProductConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductConfigRepository extends MongoRepository<ProductConfig, String> {

    Optional<ProductConfig> findByType(String category);
}
