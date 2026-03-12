package com.edgewatch.consumer.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Add this line!
public class TickerMessage {
    private String type; // Now it won't be "unrecognized"
    private String product_id;
    private String price;
    private String time;
}
