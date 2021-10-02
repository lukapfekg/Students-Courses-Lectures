package com.example.Crypto.Api;

import com.example.Crypto.Util.Ping;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;

public interface CoinGeckoApiService {
    @GET("ping")
    Call<Ping> ping();

    @GET("simple/price")
    Call<Map<String, Map<String, Double>>> getPrice(@Query("ids") String ids,
                                                    @Query("vs_currencies") String vsCurrencies,
                                                    @Query("include_market_cap") boolean includeMarketCap,
                                                    @Query("include_24hr_vol") boolean include24hrVol,
                                                    @Query("include_24hr_change") boolean include24hrChange,
                                                    @Query("include_last_updated_at") boolean includeLastUpdatedAt);

    @GET("simple/token_price/{id}")
    Call<Map<String, Map<String, Double>>> getTokenPrice(@Path("id") String id, @Query("contract_addresses") String contractAddress,
                                                         @Query("vs_currencies") String vsCurrencies, @Query("include_market_cap") boolean includeMarketCap,
                                                         @Query("include_24hr_vol") boolean include24hrVol, @Query("include_24hr_change") boolean include24hrChange,
                                                         @Query("include_last_updated_at") boolean includeLastUpdatedAt);

    @GET("simple/supported_vs_currencies")
    Call<List<String>> getSupportedVsCurrencies();

}