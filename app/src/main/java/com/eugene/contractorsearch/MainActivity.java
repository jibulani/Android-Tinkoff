package com.eugene.contractorsearch;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.eugene.contractorsearch.latest_contractors.LatestContractorsActivity;
import com.eugene.contractorsearch.network.ApiDadataServer;
import com.eugene.contractorsearch.network.RequestObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    Button latestSearchButton;
    ApiDadataServer apiDadataServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latestSearchButton = findViewById(R.id.latest_search_button);
        apiDadataServer = new ApiDadataServer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setListeners();
    }

    @Override
    protected void onPause() {
        super.onPause();
        removeListeners();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setListeners() {
        latestSearchButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LatestContractorsActivity.class);
            startActivity(intent);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void performRequest() {
        RequestObject requestObject = new RequestObject();
        requestObject.setQuery("Сбер");
        apiDadataServer.getApi().getContractors(requestObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        suggestions -> suggestions.getContractors()
                                .forEach(contractor -> System.out.println(contractor.getValue())),
                        throwable -> System.out.println(String.format("Throwable:%s", throwable.getMessage()))
                );
    }

    private void removeListeners() {
        latestSearchButton.setOnClickListener(null);
    }
}
