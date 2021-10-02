package com.example.Crypto.Model;

import lombok.Data;

@Data
public class Market {
    Coin coin;
    Double price;
    String date;

    public Market(Coin coin, double coinPrice, String date) {
        this.coin = coin;
        this.price = coinPrice;
        this.date = date;
    }
}
