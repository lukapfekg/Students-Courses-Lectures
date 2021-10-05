package com.example.Job.Jobs;

import com.example.Crypto.Api.CoinGeckoApiClient;
import com.example.Crypto.Constants.Currency;
import com.example.Crypto.Implementation.CoinGeckoApiClientImpl;
import com.example.Crypto.MVC.Model.Coin;
import com.example.Crypto.MVC.Model.Market;
import com.example.Crypto.MVC.Repository.CryptoRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public class GetCryptoValue implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            List<Coin> coins = CryptoRepository.getCoins();
            CoinGeckoApiClient client = new CoinGeckoApiClientImpl();

            for (Coin coin : coins) {
                Map<String, Map<String, Double>> newCoin = client.getPrice(coin.getCoinId(), Currency.USD, true, true, true, true);
                double coinPrice = newCoin.get(coin.getCoinId()).get(Currency.USD);
                String date = doubleToDate(newCoin.get(coin.getCoinId()).get("last_updated_at"));
                System.out.println("Coin: " + coin.getCoinName() + " - Price: " + coinPrice + " - Date: " + date);

                Market market = new Market(coin, coinPrice, date);
                CryptoRepository.newMarketValue(market);
            }

            client.shutdown();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
