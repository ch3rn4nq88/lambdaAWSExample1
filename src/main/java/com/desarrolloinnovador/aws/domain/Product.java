package com.desarrolloinnovador.aws.domain;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DynamoDBTable(tableName = "productos")
public class Product implements Serializable {

    @DynamoDBHashKey
    private String id;

    @DynamoDBAttribute
    private String title;

    @DynamoDBAttribute
    private String description;

    @DynamoDBAttribute
    private Double price;

    @DynamoDBAttribute
    private String category;

    @DynamoDBAttribute
    private String image;
}
