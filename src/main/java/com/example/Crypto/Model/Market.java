package com.example.Crypto.Model;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class Market {
    Coin coin;
    Double price;
    ZonedDateTime date;

    public Market(Coin coin, double coinPrice, ZonedDateTime date) {
        this.coin = coin;
        this.price = coinPrice;
        this.date = date;
    }
}
