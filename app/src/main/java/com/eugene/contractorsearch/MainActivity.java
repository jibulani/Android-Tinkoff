package com.eugene.contractorsearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.eugene.contractorsearch.latest_contractors.LatestContractorsActivity;
import com.eugene.contractorsearch.network.dadata.ApiDadataServer;

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
            MainActivity.this.startActivity(intent);
        });
    }

    private void removeListeners() {
        latestSearchButton.setOnClickListener(null);
    }
}
