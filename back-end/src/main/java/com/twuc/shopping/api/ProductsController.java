package com.twuc.shopping.api;


import com.twuc.shopping.dto.Products;
import com.twuc.shopping.entity.ProductsEntity;
import com.twuc.shopping.repository.ProductsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RestController
public class ProductsController {

    private  final ProductsRepository productsRepository;

    public ProductsController(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Products>> getAllProducts(@RequestParam(required = false) Integer start
            , @RequestParam(required = false) Integer end) {

        List<ProductsEntity> products = productsRepository.findAll();
        List<Products> productsList = new ArrayList<>();
        Stream.iterate(0, i -> i + 1).limit(products.size()).forEach(i -> {
            productsList.add(Products.builder()
                    .productName(products.get(i).getProductName())
                    .price(products.get(i).getPrice())
                    .unit(products.get(i).getUnit())
                    .picUrl(products.get(i).getPicUrl())
                    .build());
        });
        if (start == null || end == null){
            return ResponseEntity.ok(productsList);
        }
        return ResponseEntity.ok(productsList.subList(start - 1, end));
    }

    @PostMapping("/product")
    public ResponseEntity register(@Valid @RequestBody Products products){
        ProductsEntity productsEntity = ProductsEntity.builder()
                .productName(products.getProductName())
                .price(products.getPrice())
                .unit(products.getUnit())
                .picUrl(products.getPicUrl())
                .build();
        productsRepository.save(productsEntity);
        return ResponseEntity.created(null).build();
    }

}
