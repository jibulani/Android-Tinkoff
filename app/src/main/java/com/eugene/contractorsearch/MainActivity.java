package com.eugene.contractorsearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.eugene.contractorsearch.latest_contractors.LatestContractorsActivity;

public class MainActivity extends AppCompatActivity {

    Button latestSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latestSearchButton = findViewById(R.id.latest_search_button);
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
            startActivity(intent);
        });
    }

    private void removeListeners() {
        latestSearchButton.setOnClickListener(null);
    }
}
