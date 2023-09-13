package com.app.storage.service;

import com.app.common.dto.StorageDTO;
import com.app.storage.domain.Storage;
import com.app.storage.repository.StorageRepository;
import com.app.storage.util.mapper.StorageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final StorageRepository storageRepository;

    @Transactional(readOnly = true)
    public Page<StorageDTO> getStorages(Pageable pageable) {
        Page<Storage> storagesPage = storageRepository.findAll(pageable);
        return StorageMapper.mapPage(storagesPage);
    }


    @KafkaListener(topics = "order_topic", groupId = "order")
    @Transactional(readOnly = true)
    public List<StorageDTO> getStoragesByProductIds(List<Integer> productIds) {
        List<Storage> storages = storageRepository.findStoragesByProductIds(productIds);
        return StorageMapper.mapList(storages);
    }
}
