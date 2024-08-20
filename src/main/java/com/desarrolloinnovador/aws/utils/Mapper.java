package com.desarrolloinnovador.aws.utils;

import com.desarrolloinnovador.aws.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Mapper {
    public static Product mapProduct(String body) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(body, Product.class);
        } catch (Exception e) {
            throw e;
        }
    }


}
