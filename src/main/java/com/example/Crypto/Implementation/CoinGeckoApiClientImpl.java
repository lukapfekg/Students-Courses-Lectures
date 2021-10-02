package com.example.Crypto.Implementation;


import com.example.Crypto.Api.CoinGeckoApi;
import com.example.Crypto.Api.CoinGeckoApiClient;
import com.example.Crypto.Api.CoinGeckoApiService;
import com.example.Crypto.Util.Ping;

import java.util.List;
import java.util.Map;

public class CoinGeckoApiClientImpl implements CoinGeckoApiClient {
    private final CoinGeckoApiService coinGeckoApiService;
    private final CoinGeckoApi coinGeckoApi;

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

    @Override
    public void shutdown() {
        coinGeckoApi.shutdown();
    }
}