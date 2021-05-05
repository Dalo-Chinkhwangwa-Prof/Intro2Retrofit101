package com.coolcats.coolcatsapi3rdparty.model.network;

import android.util.Log;

import com.coolcats.coolcatsapi3rdparty.model.JikanResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class JikanRetrofit {

//https://api.jikan.moe/v3/search/anime?q=naruto
//BaseURL = https://api.jikan.moe
//Path = v3/search/anime

    private JikanService jikanService = createRetrofit().create(JikanService.class);

    private Retrofit createRetrofit(){
        return new Retrofit.Builder()
                .baseUrl("https://api.jikan.moe")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Call<JikanResponse> searchQuery(String search){

        Log.d("TAG_X", "SearchQuery..." + Thread.currentThread().getName());
        return jikanService.searchResults(search);
    }

    interface JikanService {
        @GET("v3/search/anime")
        Call<JikanResponse> searchResults(@Query("q") String query);
    }
}
