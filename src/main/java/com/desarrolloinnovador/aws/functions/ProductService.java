package com.desarrolloinnovador.aws.functions;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.desarrolloinnovador.aws.domain.RequestInputs;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class ProductService {
    private static final Logger log = Logger.getLogger(ProductService.class);
    private static final com.desarrolloinnovador.aws.services.ProductService productService= new com.desarrolloinnovador.aws.services.ProductService();

    public com.desarrolloinnovador.aws.domain.Product addProduct(com.desarrolloinnovador.aws.domain.Product product){
        log.info("Product to update " + product);
        product.setId(UUID.randomUUID().toString());
        productService.saveProduct(product);
        return product;
    }

    public APIGatewayProxyResponseEvent updateProduct(APIGatewayProxyRequestEvent apiGatewayEvent)  {
        com.desarrolloinnovador.aws.domain.Product product = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            product = objectMapper.readValue(apiGatewayEvent.getBody(), com.desarrolloinnovador.aws.domain.Product.class);
        } catch (IOException e) {
            log.error("Error",e);
        }
        product.setId(apiGatewayEvent.getPathParameters().get("productId"));
        log.info("Product to update" + product);

        productService.saveProduct(product);

        APIGatewayProxyResponseEvent response=new APIGatewayProxyResponseEvent();
        response.setStatusCode(200);
        return response;
    }

    public List<com.desarrolloinnovador.aws.domain.Product> getProducts(){
        return productService.findAllProducts();
    }

    public com.desarrolloinnovador.aws.domain.Product getProductById(String id){
        return productService.findProductById(id).orElseThrow(NoSuchElementException::new);
    }

    public void deleteProductById(RequestInputs id){
        System.out.println(id);
        productService.deleteProduct(id.getProductId());
    }
}
