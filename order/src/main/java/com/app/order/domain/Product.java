package com.app.order.domain;

import com.app.common.domain.BaseModel;
import com.app.common.enumeration.Brand;
import com.app.common.enumeration.Group;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;

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

    @Enumerated(EnumType.STRING)
    private Group group;

    @Enumerated(EnumType.STRING)
    private Brand brand;

    private BigDecimal price;
    private int count;
}
