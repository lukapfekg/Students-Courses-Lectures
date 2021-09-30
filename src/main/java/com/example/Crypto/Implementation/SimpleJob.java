package com.example.Crypto.Implementation;

import com.example.Crypto.Api.CoinGeckoApiClient;
import com.example.Crypto.Constants.Currency;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;

public class SimpleJob {
    public static void main(String[] args) {

        CoinGeckoApiClient client = new CoinGeckoApiClientImpl();

        Map<String, Map<String, Double>> bitcoin = client.getPrice("13045-wade", Currency.USD, true, true, true, true);

        System.out.println(bitcoin);

        double coinPrice = bitcoin.get("13045-wade").get(Currency.USD);
        Double lastUpdatedAt = bitcoin.get("13045-wade").get("last_updated_at");

        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(0);
        System.out.println(df.format(lastUpdatedAt));


        long epoch = Long.parseLong(df.format(lastUpdatedAt));
        Instant instant = Instant.ofEpochSecond(epoch);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);

        System.out.println(coinPrice + "      " + zonedDateTime);

        client.shutdown();
    }
}
