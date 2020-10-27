package com.twuc.shopping.api;

import com.twuc.shopping.dto.Oders;
import com.twuc.shopping.entity.OdersEntity;
import com.twuc.shopping.repository.OrdersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RestController
public class OdersController {
    private  final OrdersRepository ordersRepository;

    public OdersController(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Oders>> getAllProducts(@RequestParam(required = false) Integer start
            , @RequestParam(required = false) Integer end) {

        List<OdersEntity> orders = ordersRepository.findAll();
        List<Oders> productsList = new ArrayList<>();
        Stream.iterate(0, i -> i + 1).limit(orders.size()).forEach(i -> {
            productsList.add(Oders.builder()
                    .productName(orders.get(i).getProductName())
                    .price(orders.get(i).getPrice())
                    .unit(orders.get(i).getUnit())
                    .number(orders.get(i).getNumber())
                    .build());
        });
        if (start == null || end == null){
            return ResponseEntity.ok(productsList);
        }
        return ResponseEntity.ok(productsList.subList(start - 1, end));
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity deleteOrder(@PathVariable int id){
        if (!ordersRepository.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        ordersRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
