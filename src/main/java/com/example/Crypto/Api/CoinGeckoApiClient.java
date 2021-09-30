package com.example.Crypto.Api;


import com.example.Crypto.Util.Ping;

import java.util.List;
import java.util.Map;

public interface CoinGeckoApiClient {
    Ping ping();

    Map<String, Map<String, Double>> getPrice(String ids, String vsCurrencies);

    Map<String, Map<String, Double>> getPrice(String ids, String vsCurrencies, boolean includeMarketCap, boolean include24hrVol,
                                              boolean include24hrChange, boolean includeLastUpdatedAt);

    Map<String, Map<String, Double>> getTokenPrice(String id, String contractAddress, String vsCurrencies);

    Map<String, Map<String, Double>> getTokenPrice(String id, String contractAddress, String vsCurrencies, boolean includeMarketCap,
                                                   boolean include24hrVol, boolean include24hrChange, boolean includeLastUpdatedAt);

    List<String> getSupportedVsCurrencies();

    /*
        List<CoinList> getCoinList();

        List<CoinMarkets> getCoinMarkets(String vsCurrency);

        List<CoinMarkets> getCoinMarkets(String vsCurrency,  String ids, String order,  Integer perPage, Integer page,  boolean sparkline, String priceChangePercentage);

        CoinFullData getCoinById(String id);

        CoinFullData getCoinById(String id, boolean localization, boolean tickers, boolean marketData, boolean communityData, boolean developerData, boolean sparkline);

    */
    void shutdown();


}