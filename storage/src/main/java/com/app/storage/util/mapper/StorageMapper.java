package com.app.storage.util.mapper;

import com.app.common.dto.ResponsePage;
import com.app.common.dto.StorageDTO;
import com.app.storage.domain.Storage;
import org.springframework.data.domain.Page;

import java.util.List;

public class StorageMapper {

    private StorageMapper() {
    }

    public static StorageDTO map(Storage storage) {
        return new StorageDTO(
            storage.getAddress(),
            storage.getCity().getValue());
    }

    public static List<StorageDTO> mapList(List<Storage> storages) {
        return storages.stream()
            .map(StorageMapper::map)
            .toList();
    }

    public static Page<StorageDTO> mapPage(Page<Storage> storages) {
        return storages.map(StorageMapper::map);
    }

    public static ResponsePage<StorageDTO> mapResponsePage(Page<StorageDTO> storages) {
        return new ResponsePage<>(
            storages.getContent(),
            storages.getTotalElements()
        );
    }
}
