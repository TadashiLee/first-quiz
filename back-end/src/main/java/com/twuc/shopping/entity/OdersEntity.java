package com.twuc.shopping.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OdersEntity {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "name")
    private String productName;

    private Integer price;

    private Integer number;

    private String unit;
}
