package com.eugene.contractorsearch;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.eugene.contractorsearch.latest_contractors.LatestContractorsActivity;
import com.eugene.contractorsearch.network.dadata.ApiDadataServer;
import com.eugene.contractorsearch.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    Button latestSearchButton;
    ApiDadataServer apiDadataServer;
    AutoCompleteTextView contractorValue;
    Button settings;
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latestSearchButton = findViewById(R.id.latest_search_button);
        apiDadataServer = new ApiDadataServer();
        contractorValue = findViewById(R.id.contractor_value);
        contractorValue.setAdapter(new ContractorSearchAdapter(this));
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        settings = findViewById(R.id.settings);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        if (!BuildConfig.DEBUG) {
            settings.setVisibility(View.INVISIBLE);
        } else {
            settings.setVisibility(View.VISIBLE);
        }

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
        if (SettingTypes.bottomMenu) {
            bottomNavigationView.setOnNavigationItemSelectedListener
                    (item -> {
                        switch (item.getItemId()) {
                            case R.id.action_latest:
                                Intent intent2 = new Intent(MainActivity.this, LatestContractorsActivity.class);
                                MainActivity.this.startActivity(intent2);
                                break;
                            case R.id.action_settings:
                                Intent intent3 = new Intent(MainActivity.this, SettingsActivity.class);
                                MainActivity.this.startActivity(intent3);
                                break;
                        }
                        return true;
                    });
            bottomNavigationView.setSelectedItemId(R.id.action_home);
            bottomNavigationView.setVisibility(View.VISIBLE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            latestSearchButton.setVisibility(View.INVISIBLE);
            settings.setVisibility(View.INVISIBLE);
        } else if (SettingTypes.drawerMenu) {
            drawerLayout.closeDrawers();
            navigationView.getMenu().findItem(R.id.action_home).setChecked(true);
            navigationView.setNavigationItemSelectedListener(
                    item -> {
                        switch (item.getItemId()) {
                            case R.id.action_latest:
                                Intent intent2 = new Intent(MainActivity.this, LatestContractorsActivity.class);
                                MainActivity.this.startActivity(intent2);
                                break;
                            case R.id.action_settings:
                                Intent intent3 = new Intent(MainActivity.this, SettingsActivity.class);
                                MainActivity.this.startActivity(intent3);
                                break;
                        }
                        return true;
                    });
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            bottomNavigationView.setVisibility(View.INVISIBLE);
            latestSearchButton.setVisibility(View.INVISIBLE);
            settings.setVisibility(View.INVISIBLE);
        } else {
            latestSearchButton.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, LatestContractorsActivity.class);
                MainActivity.this.startActivity(intent);
            });
            if (BuildConfig.DEBUG) {
                settings.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    MainActivity.this.startActivity(intent);
                });
                settings.setVisibility(View.VISIBLE);
            }
            bottomNavigationView.setVisibility(View.INVISIBLE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            latestSearchButton.setVisibility(View.VISIBLE);
        }
    }

    private void removeListeners() {
        latestSearchButton.setOnClickListener(null);
        settings.setOnClickListener(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(null);
        navigationView.setNavigationItemSelectedListener(null);
    }
}
