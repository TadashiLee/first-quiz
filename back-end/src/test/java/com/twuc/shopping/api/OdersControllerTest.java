package com.twuc.shopping.api;

import com.twuc.shopping.entity.OdersEntity;
import com.twuc.shopping.repository.OrdersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OdersControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    OrdersRepository ordersRepository;

    @BeforeEach
    void setUp() {
        ordersRepository.deleteAll();
    }

    @Test
    void should_get_oder_list() throws Exception {

        OdersEntity oder1 = saveOneOdersEntity("可乐", 3, 1, "听");
        OdersEntity oder2 = saveOneOdersEntity("雪碧", 4, 2, "听");
        OdersEntity oder3 = saveOneOdersEntity("康帅傅", 5, 3, "碗");

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].productName", is("可乐")))
                .andExpect(jsonPath("$[0].price", is(3)))
                .andExpect(jsonPath("$[0].number", is(1)))
                .andExpect(jsonPath("$[0].unit", is("听")))
                .andExpect(jsonPath("$[1].productName", is("雪碧")))
                .andExpect(jsonPath("$[1].price", is(4)))
                .andExpect(jsonPath("$[2].productName", is("康帅傅")))
                .andExpect(jsonPath("$[2].price", is(5)));
    }

    @Test
    public void delet_oder_by_id() throws Exception {
        OdersEntity order = saveOneOdersEntity("可乐", 3, 1, "听");
        mockMvc.perform(delete("/orders/{id}",order.getId()))
                .andExpect(status().isNoContent());
        List<OdersEntity> users = ordersRepository.findAll();
        assertEquals(0,users.size());
    }

    private OdersEntity saveOneOdersEntity(String productName, Integer price, Integer number, String unit) {
        OdersEntity Product = OdersEntity.builder()
                .productName(productName)
                .price(price)
                .number(number)
                .unit(unit)
                .build();
        ordersRepository.save(Product);
        return Product;
    }
}
