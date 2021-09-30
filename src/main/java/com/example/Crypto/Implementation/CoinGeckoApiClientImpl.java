package com.example.Crypto.Implementation;


import com.example.Crypto.Api.CoinGeckoApi;
import com.example.Crypto.Api.CoinGeckoApiClient;
import com.example.Crypto.Api.CoinGeckoApiService;
import com.example.Crypto.Util.Ping;

import java.util.List;
import java.util.Map;

public class CoinGeckoApiClientImpl implements CoinGeckoApiClient {
    private CoinGeckoApiService coinGeckoApiService;
    private CoinGeckoApi coinGeckoApi;

    public CoinGeckoApiClientImpl() {
        this.coinGeckoApi = new CoinGeckoApi();
        this.coinGeckoApiService = coinGeckoApi.createService(CoinGeckoApiService.class);

    }

    @Override
    public Ping ping() {
        return coinGeckoApi.executeSync(coinGeckoApiService.ping());
    }

    @Override
    public Map<String, Map<String, Double>> getPrice(String ids, String vsCurrencies) {
        return getPrice(ids, vsCurrencies, false, false, false, false);
    }

    @Override
    public Map<String, Map<String, Double>> getPrice(String ids, String vsCurrencies, boolean includeMarketCap, boolean include24hrVol, boolean include24hrChange, boolean includeLastUpdatedAt) {
        return coinGeckoApi.executeSync(coinGeckoApiService.getPrice(ids, vsCurrencies, includeMarketCap, include24hrVol, include24hrChange, includeLastUpdatedAt));
    }

    @Override
    public Map<String, Map<String, Double>> getTokenPrice(String id, String contractAddress, String vsCurrencies) {
        return getTokenPrice(id, contractAddress, vsCurrencies, false, false, false, false);
    }

    @Override
    public Map<String, Map<String, Double>> getTokenPrice(String id, String contractAddress, String vsCurrencies, boolean includeMarketCap, boolean include24hrVol, boolean include24hrChange, boolean includeLastUpdatedAt) {
        return coinGeckoApi.executeSync(coinGeckoApiService.getTokenPrice(id, contractAddress, vsCurrencies, includeMarketCap, include24hrVol, include24hrChange, includeLastUpdatedAt));
    }

    @Override
    public List<String> getSupportedVsCurrencies() {
        return coinGeckoApi.executeSync(coinGeckoApiService.getSupportedVsCurrencies());
    }
/*
    @Override
    public List<CoinList> getCoinList() {
        return coinGeckoApi.executeSync(coinGeckoApiService.getCoinList());
    }

    @Override
    public List<CoinMarkets> getCoinMarkets(String vsCurrency) {
        return getCoinMarkets(vsCurrency,null,null,null,null,false,null);
    }

    @Override
    public List<CoinMarkets> getCoinMarkets(String vsCurrency, String ids, String order, Integer perPage, Integer page, boolean sparkline, String priceChangePercentage) {
        return coinGeckoApi.executeSync(coinGeckoApiService.getCoinMarkets(vsCurrency,ids,order,perPage,page,sparkline,priceChangePercentage));
    }

    @Override
    public CoinFullData getCoinById(String id) {
        return getCoinById(id,true,true,true,true,true,false);
    }

    @Override
    public CoinFullData getCoinById(String id, boolean localization, boolean tickers, boolean marketData, boolean communityData, boolean developerData, boolean sparkline) {
        return coinGeckoApi.executeSync(coinGeckoApiService.getCoinById(id,localization,tickers,marketData,communityData,developerData,sparkline));
    }


 */

    @Override
    public void shutdown() {
        coinGeckoApi.shutdown();
    }
}