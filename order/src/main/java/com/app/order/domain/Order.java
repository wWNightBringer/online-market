package com.app.order.domain;

import com.app.common.domain.BaseModel;
import com.app.common.enumeration.State;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    attributeNodes = {@NamedAttributeNode("bucket")})
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
    @Enumerated(EnumType.STRING)
    private State state;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bucket_id")
    private Bucket bucket;
}
