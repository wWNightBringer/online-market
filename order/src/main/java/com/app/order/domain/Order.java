package com.app.order.domain;

import com.app.common.domain.BaseModel;
import com.app.common.enumeration.State;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(schema = "public", name = "orders")
@Builder
@Where(clause = "is_deleted = false")
@NamedEntityGraph(
    name = Order.ORDER_ENTITY_GRAPH_NAME,
    attributeNodes = {@NamedAttributeNode("products")})
public class Order extends BaseModel {

    public static final String ORDER_ENTITY_GRAPH_NAME = "getAllOrder";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uuid;
    @Column(name = "order_number")
    private BigDecimal orderNumber;
    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;
    @Column(name = "total_cost")
    private BigDecimal totalCost;
    @Column(name = "product_count")
    private int productCount;
    @Column(name = "user_id")
    private int userId;
    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "product_order",
        joinColumns = {
            @JoinColumn(name = "order_id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "product_id")
        }
    )
    private List<Product> products;
}
