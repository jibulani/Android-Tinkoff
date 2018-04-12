package com.eugene.contractorsearch.contractor_info;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.eugene.contractorsearch.App;
import com.eugene.contractorsearch.R;
import com.eugene.contractorsearch.db.AppDatabase;
import com.eugene.contractorsearch.db.ContractorShortInfo;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContractorInfoActivity extends AppCompatActivity {

    String contractorId;
    AppDatabase appDatabase;
    TextView contractorInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);



    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.contractor_info);
        appDatabase = App.getInstance().getAppDatabase();
        contractorInfo = findViewById(R.id.contractor_info);
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            contractorId = intent.getExtras().getString("hid");
            Single.fromCallable(() -> getContractor(contractorId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(contractorShortInfo -> contractorInfo.setText(contractorShortInfo.getValue()));
        }
    }

    private ContractorShortInfo getContractor(String contractorId) {
        return appDatabase.contractorDao().getContractorById(contractorId);
    }
}
