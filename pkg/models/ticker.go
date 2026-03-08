package models

type Ticker struct{
	ProductId string `json:"product_id"`
	Price string `json:"price"`
	Time string `json:"time"`
}