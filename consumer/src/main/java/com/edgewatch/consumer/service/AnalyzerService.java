package com.edgewatch.consumer.service;

import com.edgewatch.consumer.entity.TickerEntity;
import com.edgewatch.consumer.models.TickerMessage;
import com.edgewatch.consumer.repository.TickerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnalyzerService {

    private final TickerRepository repository;
    private final ObjectMapper objectMapper; // Spring provides this for JSON

    @KafkaListener(topics = "raw-tickers", groupId = "edgewatch-analyzer")
    public void consume(String message) {
        try {
            // 1. Parse JSON to DTO
            TickerMessage dto = objectMapper.readValue(message, TickerMessage.class);

            // 2. Map DTO to Entity
            TickerEntity entity = TickerEntity.builder()
                    .time(OffsetDateTime.parse(dto.getTime()))
                    .symbol(dto.getProduct_id())
                    .price(Double.parseDouble(dto.getPrice()))
                    .build();

            // 3. Save to TimescaleDB
            repository.save(entity);
            
            log.info("Saved to DB: {} @ {}", entity.getSymbol(), entity.getPrice());

        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage());
        }
    }
}