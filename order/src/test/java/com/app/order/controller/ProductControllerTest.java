package com.app.order.controller;

import com.app.common.dto.ProductDTO;
import com.app.order.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.app.common.enumeration.Brand.LENOVO;
import static com.app.common.enumeration.Brand.SAMSUNG;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ContextConfiguration(classes = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void getProductsByItsBrand() throws Exception {
        List<ProductDTO> products = buildProducts();

        when(productService.getAllProducts(null, null, LENOVO, null, null, Pageable.unpaged())).thenReturn(products);

        mockMvc.perform(get("/api/v1/products")
                .param("brand", "LENOVO"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    private List<ProductDTO> buildProducts() {
        ProductDTO productDTO1 = new ProductDTO("test1", null, LENOVO, null);
        ProductDTO productDTO2 = new ProductDTO("test2", null, SAMSUNG, null);

        return List.of(productDTO1, productDTO2);
    }
}
