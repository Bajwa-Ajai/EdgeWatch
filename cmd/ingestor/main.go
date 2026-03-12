package main

import(
	// "fmt"
	"log"
	"encoding/json"
	"context"
	"time"

	"github.com/gorilla/websocket"
	"edgewatch/pkg/models"
	"edgewatch/internal/infra"
)

func main() {
	
	kw:=infra.NewKafkaWriter("localhost:19092","raw-tickers")

	conn,_,err:=websocket.DefaultDialer.Dial("wss://ws-feed.exchange.coinbase.com", nil)

	if err !=nil {
		log.Fatal("Connection Failed:",err)
	}
	defer conn.Close()

	sub := `{"type": "subscribe", "product_ids": ["BTC-USD"], "channels": ["ticker"]}`
	if err := conn.WriteMessage(websocket.TextMessage,[]byte(sub)); err!=nil{
		log.Fatalf("Sub error: %v",err)
	}

	log.Println("Go Ingestor Active")

	// conn.WriteMessage(websocket.TextMessage, []byte(sub))
	for {
		_,message,_:=conn.ReadMessage()

		if err != nil {
			log.Printf("Read error: %v. Reconnecting...", err)
			time.Sleep(2 * time.Second)
			continue
		}

		var t models.Ticker
		if err:=json.Unmarshal(message,&t); err!=nil{
			continue
		}
		if t.ProductId != "" {
			// We convert the ProductId to bytes for the Kafka Key
			key := []byte(t.ProductId)
			
			err = kw.Write(context.Background(), key, message)
			if err != nil {
				log.Printf("Kafka write error: %v", err)
			}
		}
	}

}