package com.example.Job.Jobs;

import com.example.Crypto.Api.CoinGeckoApiClient;
import com.example.Crypto.Constants.Currency;
import com.example.Crypto.Implementation.CoinGeckoApiClientImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;

public class GetCryptoValue implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        CoinGeckoApiClient client = new CoinGeckoApiClientImpl();

        String ids = "bitcoin";
        Map<String, Map<String, Double>> bitcoin = client.getPrice(ids, Currency.USD, true, true, true, true);

        System.out.println(bitcoin);

        double coinPrice = bitcoin.get(ids).get(Currency.USD);
        Double lastUpdatedAt = bitcoin.get(ids).get("last_updated_at");

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
