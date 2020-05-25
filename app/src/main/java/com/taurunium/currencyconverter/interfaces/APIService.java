package com.taurunium.currencyconverter.interfaces;

import com.taurunium.currencyconverter.models.MainModel;

import io.reactivex.Observable;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface  APIService {
   public static final String BASE_URL = "https://hiring.revolut.codes/api/android/";
//base=EUR
    @GET("latest?")
    Observable<MainModel> getCurrencies(@Query("base") String baseCur);
}
