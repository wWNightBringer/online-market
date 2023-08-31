package com.app.order.domain;

import com.app.common.domain.BaseModel;
import com.app.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(schema = "public", name = "buckets")
@Builder
@Where(clause = "is_deleted = false")
public class Bucket extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uuid;
    @Column(name = "total_cost")
    private BigDecimal totalCost;
    @Column(name = "product_count")
    private int productCount;
//    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id")
//    private User user;
}
