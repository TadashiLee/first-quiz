package com.twuc.shopping.repository;

import com.twuc.shopping.entity.OdersEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrdersRepository extends CrudRepository<OdersEntity,Integer> {
    List<OdersEntity> findAll();
}
