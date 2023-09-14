package com.app.storage.repository;

import com.app.storage.domain.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StorageRepository extends JpaRepository<Storage, Integer> {

    @Query("""
        SELECT storage
        FROM Storage storage
        LEFT JOIN ProductStorage ps ON storage.id=ps.productStorageKey.storageId
        WHERE ps.productStorageKey.productId in :productIds
        """)
    List<Storage> findStoragesByProductIds(@Param("productIds") Integer[] productIds);
}
