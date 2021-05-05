package com.coolcats.coolcatsapi3rdparty.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.coolcats.coolcatsapi3rdparty.CounterTask;
import com.coolcats.coolcatsapi3rdparty.databinding.ActivityMainBinding;
import com.coolcats.coolcatsapi3rdparty.model.JikanItem;
import com.coolcats.coolcatsapi3rdparty.presenter.Contract;
import com.coolcats.coolcatsapi3rdparty.presenter.JikanPresenter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Contract.View, Handler.Callback {

    private ActivityMainBinding binding;
    private JikanAdapter adapter = new JikanAdapter(new ArrayList<>());

    private Contract.Presenter presenter = new JikanPresenter(this);

    private Handler myHandler;
    private CounterTask counterTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.mainRecyclerview.setAdapter(adapter);


        myHandler = new Handler(getMainLooper(), this);

        counterTask = new CounterTask(new WeakReference<>(myHandler));

        counterTask.execute(30);

        SnapHelper sHelper = new LinearSnapHelper();
        sHelper.attachToRecyclerView(binding.mainRecyclerview);

        binding.searchButton.setOnClickListener(view -> {

            String value = binding.searchEdittext.getText().toString().trim();

            if(value.isEmpty())
                showToast("Cannot search for empty string..");
            else
                presenter.getResults(value);

        });
    }

    @Override
    public void displayResults(List<JikanItem> items) {
        adapter.setResults(items);
    }

    @Override
    public void setStatus(JikanPresenter.Status status) {
        switch (status){
            case ERROR:
                showToast("An error occurred!");
                binding.progressbar.setVisibility(View.GONE);
                break;
            case LOADING:
                binding.progressbar.setVisibility(View.VISIBLE);
                break;
            case COMPLETE:
                binding.progressbar.setVisibility(View.GONE);
                break;
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {

        switch (message.getData().getString("TYPE")){
            case "PROGRESS":
                Toast.makeText(this, "Seconds : "+ message.getData().getInt("seconds"), Toast.LENGTH_SHORT).show();
                break;
            case "COMPLETE":
                Toast.makeText(this, "Completed: You get $"+message.getData().getDouble("complete"), Toast.LENGTH_SHORT).show();
                break;
        }

        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        counterTask.cancel(true);
    }
}