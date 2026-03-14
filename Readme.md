EdgeWatch

1. The High-Level Data Journey
   The data follows a one-way path from the "Real World" to your "Eyes":

Ingestion (Go): Connects to the Coinbase WebSocket, filters the noise, and converts the stream into a reliable internal message.

Buffering (Redpanda/Kafka): Acts as a "Shock Absorber." If your Java app is slow or restarts, Kafka holds the data so nothing is lost.

Analysis (Spring Boot): Consumes the data, performs calculations (like price changes), and prepares it for storage.

Persistence (TimescaleDB): Saves the data in a time-optimized format.

Visualization (Grafana): Queries the database to draw live charts.

Phase 1: The Foundation (DevOps)
Get Redpanda and Postgres running in Docker.

use docker compose up to create the containers

we should be able to open the Redpanda Console in your browser and see an empty "raw-tickers" topic.

Phase 2: The Producer (Go)

Connected to Coinbase and printed live prices to the terminal.

Connected that Go app to Kafka and observed messages appearing in the Redpanda Console.

Phase 3: The Consumer (Java/Spring Boot)

Wrote a @KafkaListener that reads the messages Go is sending.

Implemented the logic to save these messages into TimescaleDB.

The Visuals (Grafana)
Connected Grafana to the database.

Built a "Candlestick" or "Line Chart" that updates every few seconds as Bitcoin prices move.
