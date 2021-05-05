package com.coolcats.coolcatsapi3rdparty.presenter;

import android.util.Log;

import com.coolcats.coolcatsapi3rdparty.model.JikanResponse;
import com.coolcats.coolcatsapi3rdparty.model.network.JikanRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JikanPresenter implements Contract.Presenter {
    private Contract.View view;

    private JikanRetrofit jikanRetrofit = new JikanRetrofit();

    public JikanPresenter(Contract.View view) {
        this.view = view;
    }

    @Override
    public void getResults(String query) {
        view.setStatus(Status.LOADING);
        jikanRetrofit.searchQuery(query).enqueue(new Callback<JikanResponse>() {
            @Override
            public void onResponse(Call<JikanResponse> call, Response<JikanResponse> response) {

                Log.d("TAG_X", "onResponse -> " + Thread.currentThread().getName());
                if(response.body()!= null && response.body().getResults() != null){
                    view.displayResults(response.body().getResults());
                    view.setStatus(Status.COMPLETE);
                } else{
                    Log.d("TAG_X", "Empty or null.. :(");
                    view.setStatus(Status.ERROR);
                }
            }

            @Override
            public void onFailure(Call<JikanResponse> call, Throwable t) {
                //

                Log.d("TAG_X", t.toString()+" \nurl:"+call.request().url());
                view.setStatus(Status.ERROR);
            }
        });
    }

    public enum Status{
        LOADING,
        COMPLETE,
        ERROR
    }
}
