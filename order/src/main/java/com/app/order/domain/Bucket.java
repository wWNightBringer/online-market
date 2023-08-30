package com.app.order.domain;

import com.app.common.domain.BaseModel;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

//@Entity
//@Table(name = "bucket")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Bucket extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String uuid;
    private BigDecimal totalCost;
    private int productCount;
    private int userId;
}
