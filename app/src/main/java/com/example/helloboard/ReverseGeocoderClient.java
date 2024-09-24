package com.example.helloboard;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReverseGeocoderClient {
    private static ReverseGeocoderClient instance = null;
    private static ReverseGeocoderInterface reverseGeocoderInterface;
    private static String baseUrl = "https://naveropenapi.apigw.ntruss.com/";

    private ReverseGeocoderClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();
        reverseGeocoderInterface = retrofit.create(ReverseGeocoderInterface.class);
    }

    public static ReverseGeocoderClient getInstance(){
        if(instance == null){
            instance = new ReverseGeocoderClient();
        }
        return instance;
    }

    public static ReverseGeocoderInterface getReverseGeocoderInterface(){
        return reverseGeocoderInterface;
    }
}
