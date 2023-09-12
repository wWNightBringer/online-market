package com.app.order.domain;

import com.app.common.domain.BaseModel;
import com.app.common.enumeration.State;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    attributeNodes = {
        @NamedAttributeNode("productOrders")})
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
    private int totalCount;
    @Column(name = "user_id")
    private int userId;
    @Enumerated(EnumType.STRING)
    private State state;

    @JoinTable(name = "product_order",
        joinColumns = {
            @JoinColumn(name = "order_id", insertable = false, updatable = false)
        },
        inverseJoinColumns = {
            @JoinColumn(name = "product_id", insertable = false, updatable = false)
        })
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Product> products;

    @JoinColumn(name = "order_id")
    @OneToMany
    private List<ProductOrder> productOrders;
}
