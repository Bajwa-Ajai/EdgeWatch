Project Workflow: EdgeWatch Real-Time Pipeline
This project is built as a Distributed Data Pipeline. Instead of one giant application, we divide the work into specialized services that communicate through Kafka. This is exactly how modern high-scale systems (like Uber or Netflix) are designed.

1. The High-Level Data Journey
   The data follows a one-way path from the "Real World" to your "Eyes":

Ingestion (Go): Connects to the Coinbase WebSocket, filters the noise, and converts the stream into a reliable internal message.

Buffering (Redpanda/Kafka): Acts as a "Shock Absorber." If your Java app is slow or restarts, Kafka holds the data so nothing is lost.

Analysis (Spring Boot): Consumes the data, performs calculations (like price changes), and prepares it for storage.

Persistence (TimescaleDB): Saves the data in a time-optimized format.

Visualization (Grafana): Queries the database to draw live charts.

2. Step-by-Step Build Plan
   Since you want to build this one step at a time, we will follow this order:

Phase 1: The Foundation (DevOps)
We start by getting the "Environment" ready. You cannot write code if there is nowhere for the data to go.

Goal: Get Redpanda and Postgres running in Docker.

Verification: You should be able to open the Redpanda Console in your browser and see an empty "raw-tickers" topic.

Phase 2: The Producer (Go)
This is your first "New Language" challenge. We focus on networking and concurrency.

Goal: Successfully connect to Coinbase and print live prices to your terminal.

Goal: Connect that Go app to Kafka and see messages appearing in the Redpanda Console.

Phase 3: The Consumer (Java/Spring Boot)
Now you use your expertise to "Drain the Pipe."

Goal: Write a @KafkaListener that reads the messages Go is sending.

Goal: Implement the logic to save these messages into TimescaleDB.

Phase 4: The Visuals (Grafana)
The "CV-ready" part.

Goal: Connect Grafana to your database.

Goal: Build a "Candlestick" or "Line Chart" that updates every few seconds as Bitcoin prices move.
