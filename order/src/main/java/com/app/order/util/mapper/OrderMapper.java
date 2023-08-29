package com.app.order.util.mapper;

import com.app.common.dto.CreateProductDTO;
import com.app.common.dto.ProductDTO;
import com.app.order.domain.Product;
import org.springframework.data.domain.Page;

public class OrderMapper {

    private OrderMapper(){}

    public static ProductDTO map(Product product) {
        return new ProductDTO(
            product.getUuid(),
            product.getTitle(),
            product.getGroup(),
            product.getBrand(),
            product.getPrice(),
            product.getCount());
    }

    public static Page<ProductDTO> pageMap(Page<Product> products){
        return products.map(OrderMapper::map);
    }

    public static Product createMap(CreateProductDTO createProductDTO) {
        return Product.builder()
            .uuid(createProductDTO.uuid())
            .title(createProductDTO.title())
            .group(createProductDTO.group())
            .brand(createProductDTO.brand())
            .price(createProductDTO.price())
            .count(createProductDTO.count())
            .build();
    }
}
