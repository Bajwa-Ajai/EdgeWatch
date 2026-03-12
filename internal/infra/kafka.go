package infra

import(
	"context"
	"github.com/segmentio/kafka-go"
)

type KafkaWriter struct{
	Writer *kafka.Writer
}

func NewKafkaWriter(broker,topic string) *KafkaWriter{
	return &KafkaWriter{
		Writer:&kafka.Writer{
			Addr: kafka.TCP(broker),
			Topic: topic,
			Balancer :&kafka.LeastBytes{},
		},
	}
}

func (k *KafkaWriter) Write(ctx context.Context,key,value []byte)error{
	return k.Writer.WriteMessages(ctx,kafka.Message{
		Key:key,
		Value:value,
	})
}

func (k *KafkaWriter) Close() error {
	return k.Writer.Close()
}