package com.app.order.domain;

import com.app.common.domain.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(schema = "public", name = "buckets")
@Builder
@Where(clause = "is_deleted = false")
@NamedEntityGraph(
    name = Bucket.BUCKET_ENTITY_GRAPH_NAME,
    attributeNodes = {@NamedAttributeNode("products")})
public class Bucket extends BaseModel {

    public static final String BUCKET_ENTITY_GRAPH_NAME = "getAllBuckets";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uuid;
    @Column(name = "total_cost")
    private BigDecimal totalCost;
    @Column(name = "product_count")
    private int productCount;
    @Column(name = "user_id")
    private int userId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "product_bucket",
        joinColumns = {
            @JoinColumn(name = "bucket_id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "product_id")
        }
    )
    private List<Product> products;
}
