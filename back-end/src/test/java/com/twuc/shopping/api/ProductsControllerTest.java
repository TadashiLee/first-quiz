package com.twuc.shopping.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twuc.shopping.dto.Products;
import com.twuc.shopping.entity.ProductsEntity;
import com.twuc.shopping.repository.ProductsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductsRepository productsRepository;

    @BeforeEach
    void setUp() {
        productsRepository.deleteAll();
    }

    @Test
    void should_get_rs_list() throws Exception {

        ProductsEntity product1 = saveOneProductsEntity("可乐", 3, "rmb", "pic1");
        ProductsEntity product2 = saveOneProductsEntity("雪碧", 4, "rmb", "pic2");
        ProductsEntity product3 = saveOneProductsEntity("康帅傅", 5, "rmb", "pic3");

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].productName", is("可乐")))
                .andExpect(jsonPath("$[0].price", is(3)))
                .andExpect(jsonPath("$[0].unit", is("rmb")))
                .andExpect(jsonPath("$[0].picUrl", is("pic1")))
                .andExpect(jsonPath("$[1].productName", is("雪碧")))
                .andExpect(jsonPath("$[1].price", is(4)))
                .andExpect(jsonPath("$[2].productName", is("康帅傅")))
                .andExpect(jsonPath("$[2].price", is(5)));
    }

    @Test
    void should_get_rs_list_by_range() throws Exception {

        ProductsEntity product1 = saveOneProductsEntity("可乐", 3, "听", "pic1");
        ProductsEntity product2 = saveOneProductsEntity("雪碧", 4, "听", "pic2");
        ProductsEntity product3 = saveOneProductsEntity("康帅傅", 5, "碗", "pic3");

        mockMvc.perform(get("/products?start=1&end=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].productName", is("可乐")))
                .andExpect(jsonPath("$[0].price", is(3)))
                .andExpect(jsonPath("$[0].unit", is("听")))
                .andExpect(jsonPath("$[0].picUrl", is("pic1")))
                .andExpect(jsonPath("$[1].productName", is("雪碧")))
                .andExpect(jsonPath("$[1].price", is(4)))
                .andExpect(jsonPath("$[2].productName", is("康帅傅")))
                .andExpect(jsonPath("$[2].price", is(5)));
    }

    @Test
    public void should_add_products() throws Exception {
        Products product = new Products("火腿肠", 10,"根", "pic4");
        ObjectMapper objectMapper = new ObjectMapper();
        String userDtoJson = objectMapper.writeValueAsString(product);

        mockMvc.perform(post("/product")
                .content(userDtoJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        List<ProductsEntity> products = productsRepository.findAll();
        assertEquals(1, products.size());
        assertEquals("火腿肠", products.get(0).getProductName());
    }

    private ProductsEntity saveOneProductsEntity(String productName, Integer price, String unit, String picUrl) {
        ProductsEntity Product = ProductsEntity.builder()
                .productName(productName)
                .price(price)
                .unit(unit)
                .picUrl(picUrl)
                .build();
        productsRepository.save(Product);
        return Product;
    }
}
