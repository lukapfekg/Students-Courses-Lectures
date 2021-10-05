package com.example.Crypto.Service;

import com.example.Crypto.Api.CoinGeckoApiClient;
import com.example.Crypto.Constants.Currency;
import com.example.Crypto.Implementation.CoinGeckoApiClientImpl;
import com.example.Crypto.Model.Coin;
import com.example.Crypto.Model.Market;
import com.example.Job.Repository.CryptoRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CryptoService {

    @Cacheable("cryptos")
    public List<Market> getNewPrices() {
        List<Market> markets = new ArrayList<>();

        try {
            List<Coin> coins = CryptoRepository.getCoins();
            CoinGeckoApiClient client = new CoinGeckoApiClientImpl();

            for (Coin coin : coins) {
                Map<String, Map<String, Double>> newCoin = client.getPrice(coin.getCoinId(), Currency.USD, true, true, true, true);
                double coinPrice = newCoin.get(coin.getCoinId()).get(Currency.USD);
                String date = doubleToDate(newCoin.get(coin.getCoinId()).get("last_updated_at"));
                System.out.println("Coin: " + coin.getCoinName() + " - Price: " + coinPrice + " - Date: " + date);

                markets.add(new Market(coin, coinPrice, date));

            }

            client.shutdown();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return markets;
    }


    private String doubleToDate(Double date) {
        ZonedDateTime zdt = doubleToZDT(date);
        String d = zdt.toString();
        d = d.replace("T", " ");
        d = d.replace("Z", "");
        return d;
    }


    private ZonedDateTime doubleToZDT(Double date) {
        Long epoch = convertDoubleToLOng(date);
        Instant instant = Instant.ofEpochSecond(epoch);
        return ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

    private Long convertDoubleToLOng(Double d) {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(0);
        return Long.parseLong(df.format(d));
    }
}
