package com.edgewatch.consumer.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "ticker_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TickerEntity {
    @Id
    private OffsetDateTime time; // TimescaleDB uses TIMESTAMPTZ
    private String symbol;
    private Double price;
}