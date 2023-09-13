package com.app.storage.domain;

import com.app.common.domain.BaseModel;
import com.app.common.enumeration.City;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.List;


@Getter
@Setter
@Builder
@Entity
@Table(schema = "public", name = "storages")
@Where(clause = "is_deleted = false")
@AllArgsConstructor
@NoArgsConstructor
public class Storage extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String address;
    @Enumerated(EnumType.STRING)
    private City city;

    @OneToMany
    @JoinColumn(name = "storage_id")
    private List<ProductStorage> productStorages;

    public void setAddress(String street, City city) {
        this.address = String.format("city %s, %s", city, street);
    }
}
