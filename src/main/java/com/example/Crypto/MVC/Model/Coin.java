package com.example.Crypto.MVC.Model;

import lombok.Data;

@Data
public class Coin {
    Long id;
    String coinId;
    String coinName;
    String coinSymbol;

    public Coin(String coinId, String coinName, String coinSymbol) {
        this.id = null;
        this.coinId = coinId;
        this.coinName = coinName;
        this.coinSymbol = coinSymbol;
    }

    public Coin(Long id, String coinId, String coinName, String coinSymbol) {
        this.id = id;
        this.coinId = coinId;
        this.coinName = coinName;
        this.coinSymbol = coinSymbol;
    }

}
