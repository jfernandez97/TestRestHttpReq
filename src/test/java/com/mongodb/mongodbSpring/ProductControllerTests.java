package com.mongodb.mongodbSpring;

import com.mongodb.mongodbSpring.model.Product;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.util.Assert;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTests {

    private String url;
    @LocalServerPort
    private int port;
    private static long start;
    private static long end;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    static void setup(){
        System.out.println("@BeforeAll - se ejecuta antes de todos los tests");
        start = System.nanoTime();
    }

    @BeforeEach
    void init(){
        url=String.format("http://localhost:%d/products",port);
        System.out.println("@BeforeEach - se ejecuta antes de la ejecucion de cada test");
    }
    @Test
    public void getAllProducts() throws Exception{
        var uriTest=String.format("%s%s",url,"");
        var productResult=this.restTemplate.getForObject(uriTest, List.class);

        Assert.notNull(productResult,"Lista de productos no nula");
        Assert.notEmpty(productResult,"Lista de productos con elementos");
        Assert.isTrue(productResult.size() == 7,"Tamaño de la lista es de "+ productResult.size());
    }
    @Test
    public void getByName(){
        var uriTest = String.format("%s%s",url,"prueba");
        var productResult = this.restTemplate.getForObject(uriTest, Product.class);

        Assert.notNull(productResult,"Producto no nulo");
        Assert.isTrue(productResult.getStock()==0,"El stock del producto Ok");
    }
    @Test
    public void createProduct(){
        var uriTest =String.format("%s%s",url,"");
        var product= Product.builder().name("Producto prueba").price(100).stock(2).build();

        var productResult=this.restTemplate.postForObject(uriTest,product,Product.class);

        Assert.notNull(productResult,"Producto no nulo");
        Assert.isTrue(productResult.getName().equals("Producto prueba"),"Name del producto Ok");
        Assert.isTrue(productResult.getPrice()==100,"El price del product OK");
    }
    @Test
    public void getAllProductsHttpRequestStatus() throws IOException{
        var uriTest=String.format("%s%s",url,"");
        var req= new HttpGet(uriTest);
        var httpResp= HttpClientBuilder.create().build().execute(req);

        Assert.isTrue(httpResp.getStatusLine().getStatusCode()== HttpStatus.SC_OK,"Response status OK");
    }
    @AfterAll
    public static void end(){
        end=System.nanoTime();
        System.out.println("Se ejecutó el metodo con una duración de : "+ TimeUnit.NANOSECONDS.toSeconds(end-start)+" s ");
    }

}
