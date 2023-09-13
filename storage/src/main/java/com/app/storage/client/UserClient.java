package com.app.storage.client;

import com.app.common.dto.UserDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserClient {

    private final WebClient webClient;

    public UserClient(@Qualifier("webUserClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public UserDTO getUserByEmail(String email, String token) {
        return webClient.get()
            .uri("/api/v1/users/" + email)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .retrieve()
            .onStatus(HttpStatusCode::is5xxServerError, response ->
                response.createException()
                    .flatMap(Mono::error))
            .onStatus(HttpStatusCode::is4xxClientError, response ->
                response.createException()
                    .flatMap(Mono::error))
            .bodyToMono(UserDTO.class)
            .block();
    }
}
