package com.twuc.shopping.repository;

import com.twuc.shopping.entity.ProductsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductsRepository extends CrudRepository<ProductsEntity,Integer> {
    List<ProductsEntity> findAll();
}
