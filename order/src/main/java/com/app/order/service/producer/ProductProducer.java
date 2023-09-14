package com.app.order.service.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

import static com.app.common.constant.ServicesMetadata.ORDER_TOPIC;

@Service
@RequiredArgsConstructor
public class ProductProducer<T> {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public Mono<T> sendMessageToStorage(String message) {
        CompletableFuture<SendResult<String, String>> send = kafkaTemplate.send(ORDER_TOPIC, message);
        return Mono.empty();
    }
}
