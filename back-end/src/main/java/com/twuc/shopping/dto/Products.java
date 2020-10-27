package com.twuc.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Products {
    @NotEmpty
    private String productName;

    @NotNull
    private Integer price;

    @NotEmpty
    private String unit;

    @NotEmpty
    private String picUrl;
}
