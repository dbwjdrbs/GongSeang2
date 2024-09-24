package com.example.helloboard;

import com.example.helloboard.Data.Address;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ReverseGeocoderInterface {

        @GET("map-reversegeocode/v2/gc")
    Call<Address>
    getAddress(@Query("coords") String coords,
               @Query("output") String output,
               @Query("orders") String orders,
               @Query("X-NCP-APIGW-API-KEY-ID") String key_id,
               @Query("X-NCP-APIGW-API-KEY") String key);

}
