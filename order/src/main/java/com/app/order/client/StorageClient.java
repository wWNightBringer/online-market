package com.app.order.client;

import com.app.common.dto.StorageDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class StorageClient {

    private final WebClient webClient;

    public StorageClient(@Qualifier("webStorageClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public StorageDTO[] getStoragesByProductIds(List<Integer> productIds) {
        return webClient.get()
            .uri(uriBuilder ->
                uriBuilder
                    .path("/api/v1/storages/productIds")
                    .queryParam("productIds", productIds)
                    .build())
            .retrieve()
            .onStatus(HttpStatusCode::is5xxServerError, response ->
                response.createException()
                    .flatMap(Mono::error))
            .onStatus(HttpStatusCode::is4xxClientError, response ->
                response.createException()
                    .flatMap(Mono::error))
            .bodyToMono(StorageDTO[].class)
            .block();
    }
}
