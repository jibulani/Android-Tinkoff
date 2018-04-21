package com.eugene.contractorsearch.settings;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;

import com.eugene.contractorsearch.R;
import com.eugene.contractorsearch.SettingTypes;
import com.eugene.contractorsearch.latest_contractors.LatestContractorsActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch bottomSwitch;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        bottomSwitch = findViewById(R.id.switch1);
        if (SettingTypes.bottomMenu) {
            bottomSwitch.setChecked(true);
        }
        bottomSwitch.setOnClickListener(v -> {
            if (bottomSwitch.isChecked()) {
                SettingTypes.bottomMenu = true;
            } else {
                SettingTypes.bottomMenu = false;
            }
            setListeners();
        });
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.INVISIBLE);
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
                            case R.id.action_home:
                                finish();
                                break;
                            case R.id.action_latest:
                                Intent intent2 = new Intent(SettingsActivity.this, LatestContractorsActivity.class);
                                SettingsActivity.this.startActivity(intent2);
                                finish();
                                break;
                        }
                        return true;
                    });
            bottomNavigationView.setSelectedItemId(R.id.action_settings);
            bottomNavigationView.setVisibility(View.VISIBLE);
        } else {
            bottomNavigationView.setVisibility(View.INVISIBLE);
        }
    }

    private void removeListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(null);
    }
}
