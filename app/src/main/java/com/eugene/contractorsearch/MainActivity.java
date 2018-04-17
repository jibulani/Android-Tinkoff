package com.eugene.contractorsearch;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.eugene.contractorsearch.latest_contractors.LatestContractorsActivity;
import com.eugene.contractorsearch.network.dadata.ApiDadataServer;
import com.eugene.contractorsearch.network.dadata.RequestObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    Button latestSearchButton;
    ApiDadataServer apiDadataServer;
    AutoCompleteTextView contractorValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latestSearchButton = findViewById(R.id.latest_search_button);
        apiDadataServer = new ApiDadataServer();
        contractorValue = findViewById(R.id.contractor_value);
        contractorValue.setAdapter(new ContractorSearchAdapter(this));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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

    private void setListeners() {
        latestSearchButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LatestContractorsActivity.class);
            startActivity(intent);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void performRequest(String text) {
        RequestObject requestObject = new RequestObject();
        requestObject.setQuery(text);
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
