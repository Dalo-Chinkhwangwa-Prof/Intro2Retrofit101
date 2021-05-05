package com.coolcats.coolcatsapi3rdparty;

import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;

public class CounterTask extends AsyncTask<Integer, Integer, Double> {

    private WeakReference<Handler> handler;

    public CounterTask(WeakReference<Handler> handler) {
        this.handler = handler;
    }

    @Override
    protected Double doInBackground(Integer... seconds) {

        for(int i = 1; i < seconds[0]+1; i++){

            try {
                Thread.sleep(1000);
                Log.d("TAG_X", i + " seconds");
                publishProgress(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return 5000.0;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        Message progress = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("TYPE", "PROGRESS");
        bundle.putInt("seconds", values[0]);
        progress.setData(bundle);
        handler.get().sendMessage(progress);

    }


    @Override
    protected void onPostExecute(Double aDouble) {
        super.onPostExecute(aDouble);
        Message progress = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("TYPE", "COMPLETE");
        bundle.putDouble("complete", aDouble);
        progress.setData(bundle);
        handler.get().sendMessage(progress);
    }
}
