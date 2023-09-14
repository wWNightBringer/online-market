package com.app.storage.controller;

import com.app.common.dto.StorageDTO;
import com.app.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/storages")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @GetMapping("/productIds")
    public ResponseEntity<List<StorageDTO>> getStoragesByProductIds(@RequestParam("productIds") List<Integer> productIds) {
        List<StorageDTO> storagesByProductIds = storageService.getStoragesByProductIds(productIds);
        return ResponseEntity.ok(storagesByProductIds);
    }
}
