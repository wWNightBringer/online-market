package com.app.order.domain;

import com.app.common.domain.BaseModel;
import com.app.common.enumeration.Brand;
import com.app.common.enumeration.Group;
import com.app.order.service.listener.ProductListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;

@EntityListeners(ProductListener.class)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "public", name = "products")
@Builder
@Where(clause = "is_deleted = false")
public class Product extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uuid;
    private String title;
    @Column(name = "product_group")
    @Enumerated(EnumType.STRING)
    private Group group;
    @Column(name = "product_brand")
    @Enumerated(EnumType.STRING)
    private Brand brand;
    private BigDecimal price;
    private int count;
}
