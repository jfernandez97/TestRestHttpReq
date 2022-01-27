package com.mongodb.mongodbSpring.repository;

import com.mongodb.mongodbSpring.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public abstract class ProductRepositoryImpl implements ProductRepository{

    @Autowired
    MongoTemplate template;

    @Override
    public Product findByName(String name){
        var query=new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return template.find(query,Product.class).get(0);
    }
    @Override
    public List<Product> findByStockGreaterThan(int stock){
        var query=new Query();
        query.addCriteria(Criteria.where("stock").gt(stock));
        return template.find(query,Product.class);
    }
}
