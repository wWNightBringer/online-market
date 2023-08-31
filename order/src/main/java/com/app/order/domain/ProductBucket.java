package com.app.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "product_bucket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductBucket {

    @EmbeddedId
    private ProductBucketKey productBucketKey;

    @Column(name = "product_count")
    private Integer productCount;

    @Getter
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    static class ProductBucketKey implements Serializable {

        @Column(name = "product_id")
        private Integer productId;

        @Column(name = "bucket_id")
        private Integer bucketId;
    }
}
