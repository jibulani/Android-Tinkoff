package com.eugene.contractorsearch.latest_contractors;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eugene.contractorsearch.R;

public class ContractorFragment extends Fragment {

    private RecyclerView recyclerView;
    private ContractorAdapter contractorAdapter;

    public static ContractorFragment newInstance() {
        ContractorFragment contractorFragment = new ContractorFragment();
        return contractorFragment;
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    protected ContractorAdapter getContractorAdapter() {
        return contractorAdapter != null ? contractorAdapter : new ContractorAdapter();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contractors_recycler_view, container, false);

        recyclerView = rootView.findViewById(R.id.contractor_list);
        recyclerView.setLayoutManager(getLayoutManager());

        recyclerView.getItemAnimator().setAddDuration(700);
        recyclerView.getItemAnimator().setChangeDuration(700);
        recyclerView.getItemAnimator().setMoveDuration(700);
        recyclerView.getItemAnimator().setRemoveDuration(700);

        contractorAdapter = getContractorAdapter();
        //contractorAdapter.setDataFromDb();
        recyclerView.setAdapter(contractorAdapter);
        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateAdapterData() {
        getContractorAdapter().setDataFromDb();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
