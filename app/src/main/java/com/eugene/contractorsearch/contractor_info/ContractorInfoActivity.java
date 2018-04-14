package com.eugene.contractorsearch.contractor_info;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.eugene.contractorsearch.App;
import com.eugene.contractorsearch.ContractorSearchAdapter;
import com.eugene.contractorsearch.R;
import com.eugene.contractorsearch.db.AppDatabase;
import com.eugene.contractorsearch.db.ContractorShortInfo;
import com.eugene.contractorsearch.map.MapActivity;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContractorInfoActivity extends AppCompatActivity {

    String contractorId;
    AppDatabase appDatabase;
    TextView contractorInfo;
    Button mapButton;
    ContractorShortInfo contractorShortInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contractor_info);
        mapButton = findViewById(R.id.map_button);
        appDatabase = App.getInstance().getAppDatabase();
        contractorInfo = findViewById(R.id.contractor_info);
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            contractorId = intent.getExtras().getString(ContractorSearchAdapter.CONTRACTOR_ID);
            Single.fromCallable(() -> getContractor(contractorId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(loadedContractorShortInfo -> {
                        contractorInfo.setText(loadedContractorShortInfo.getValue());
                        contractorShortInfo = loadedContractorShortInfo;
                    });
        }
        mapButton.setOnClickListener(v -> {
            if (contractorShortInfo != null && contractorShortInfo.getCoordinates() != null) {
                Bundle bundle = new Bundle();
                bundle.putDouble("lat", contractorShortInfo.getCoordinates().getLat());
                bundle.putDouble("lng", contractorShortInfo.getCoordinates().getLng());
                Intent newIntent = new Intent(v.getContext(), MapActivity.class);
                newIntent.putExtras(bundle);
                startActivity(newIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private ContractorShortInfo getContractor(String contractorId) {
        return appDatabase.contractorDao().getContractorById(contractorId);
    }
}
