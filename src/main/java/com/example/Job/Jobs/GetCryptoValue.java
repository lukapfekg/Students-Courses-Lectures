package com.example.Job.Jobs;

import com.example.Crypto.Api.CoinGeckoApiClient;
import com.example.Crypto.Constants.Currency;
import com.example.Crypto.Implementation.CoinGeckoApiClientImpl;
import com.example.Crypto.Model.Coin;
import com.example.Crypto.Model.Market;
import com.example.Job.JobsRepository.CryptoRepository;
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
                ZonedDateTime date = doubleToZDT(newCoin.get(coin.getCoinId()).get("last_updated_at"));


                System.out.println(coinPrice + "      " + date);

                Market market = new Market(coin, coinPrice, date);

                CryptoRepository.newMarketValue(market);
            }

            client.shutdown();
/*
            String ids = "ethereum";
            Map<String, Map<String, Double>> bitcoin = client.getPrice(ids, Currency.USD, true, true, true, true);

            System.out.println(bitcoin);


            Double lastUpdatedAt = bitcoin.get(ids).get("last_updated_at");

*/
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    private ZonedDateTime doubleToZDT(Double date){
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(0);
        System.out.println(df.format(date));

        long epoch = Long.parseLong(df.format(date));
        Instant instant = Instant.ofEpochSecond(epoch);
        return ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
    }


}
