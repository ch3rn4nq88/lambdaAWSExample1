package com.desarrolloinnovador.aws.functions.sqs;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;

import com.desarrolloinnovador.aws.services.ProductService;
import com.desarrolloinnovador.aws.utils.Mapper;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.UUID;

public class SQSProductHandler implements RequestHandler<SQSEvent, Void> {
    private static final Logger log = Logger.getLogger(SQSProductHandler.class);
    private static final ProductService productService= new ProductService();
    @SneakyThrows
    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {

        for (SQSMessage msg : sqsEvent.getRecords()) {
            processMessage(msg, context);
        }
        log.info("done");
        return null;
    }

    private void processMessage(SQSMessage msg, Context context) throws IOException {
        var product = Mapper.mapProduct(msg.getBody());
        product.setId(UUID.randomUUID().toString());
        productService.saveProduct(product);
        log.info("Product added successfully");
    }
}
