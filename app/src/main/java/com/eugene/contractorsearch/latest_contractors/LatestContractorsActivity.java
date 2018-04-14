package com.eugene.contractorsearch.latest_contractors;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.eugene.contractorsearch.R;

public class LatestContractorsActivity extends AppCompatActivity {

    private static final String CONTRACTOR_FRAGMENT_TAG = "contractor_fragment";
    private ContractorFragment contractorFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.latest_contractors);
        System.out.println("in onCreate");
        if (savedInstanceState == null) {
            contractorFragment = ContractorFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contractors_container, contractorFragment, CONTRACTOR_FRAGMENT_TAG)
                    .commit();
        } else {
            contractorFragment = (ContractorFragment) getSupportFragmentManager()
                    .findFragmentByTag(CONTRACTOR_FRAGMENT_TAG);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.latest_contractors);
        System.out.println("in onResume");
        contractorFragment = ContractorFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contractors_container, contractorFragment, CONTRACTOR_FRAGMENT_TAG)
                .commit();
    }
}
