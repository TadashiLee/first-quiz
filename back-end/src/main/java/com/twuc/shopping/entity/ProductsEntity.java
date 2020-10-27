package com.twuc.shopping.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductsEntity {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "name")
    private String productName;

    private Integer price;

    private String unit;

    private String picUrl;
}
