package com.desarrolloinnovador.aws.services;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import com.desarrolloinnovador.aws.domain.Product;
import com.desarrolloinnovador.aws.manager.DynamoDBManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class ProductService {
    private static final DynamoDBMapper mapper = DynamoDBManager.mapper();
    private static final Logger log = Logger.getLogger(ProductService.class);

    public List<Product> findAllProducts() {
        return mapper.scan(Product.class, new DynamoDBScanExpression());
    }

    public void saveProduct(Product product) {
        mapper.save(product);
    }

    public Optional<Product> findProductById(String id) {
        var event = mapper.load(Product.class, id);
        return Optional.ofNullable(event);
    }

    public void updateProduct(Product product){
        mapper.save(product);
    }

    public void deleteProduct(String productId) {
        var producto = findProductById(productId);
        if (producto.isPresent()) {
            mapper.delete(producto.get());
        }
        else {
            log.info("Could not delete Product, no such product in the database");
            throw new IllegalArgumentException("Delete failed for nonexistent product");
        }
    }
}
