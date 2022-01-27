package com.mongodb.mongodbSpring.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("product")
@Data
@Builder
public class Product {
    @Id
    private String id;
    private String name;
    private Integer price;
    private int stock;

    public Product(String id, String name, Integer price, int stock) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
}
