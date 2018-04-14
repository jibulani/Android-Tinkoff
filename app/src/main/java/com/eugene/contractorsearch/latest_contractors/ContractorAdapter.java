package com.eugene.contractorsearch.latest_contractors;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eugene.contractorsearch.App;
import com.eugene.contractorsearch.R;
import com.eugene.contractorsearch.db.AppDatabase;
import com.eugene.contractorsearch.db.ContractorShortInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContractorAdapter extends RecyclerView.Adapter<BaseViewHolder<ContractorShortInfo>> {

    private List<ContractorShortInfo> contractorList;
    private AppDatabase appDatabase;

    public ContractorAdapter() {
        contractorList = new ArrayList<>();
        appDatabase = App.getInstance().getAppDatabase();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setDataFromDb() {
        contractorList.clear();
        Single.fromCallable(this::loadContractors)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contractorShortInfoList -> {
                    contractorList.addAll(contractorShortInfoList);
                    contractorList = sortContractors(contractorList);
                    System.out.println(contractorShortInfoList);
                    notifyDataSetChanged();
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<ContractorShortInfo> sortContractors(List<ContractorShortInfo> contractorList) {
        List<ContractorShortInfo> favoriteContractors = contractorList.stream()
                .filter(ContractorShortInfo::isFavourite).collect(Collectors.toList());
        List<ContractorShortInfo> notFavoriteContractors = contractorList.stream()
                .filter(contractorShortInfo -> !contractorShortInfo.isFavourite())
                .collect(Collectors.toList());
        favoriteContractors = favoriteContractors.stream().sorted((contractor1, contractor2) ->
                contractor2.getLastRequestDate().compareTo(contractor1.getLastRequestDate())).collect(Collectors.toList());
        notFavoriteContractors = notFavoriteContractors.stream().sorted((contractor1, contractor2) ->
                contractor2.getLastRequestDate().compareTo(contractor1.getLastRequestDate())).collect(Collectors.toList());
        List<ContractorShortInfo> sortedContractors = favoriteContractors;
        sortedContractors.addAll(notFavoriteContractors);
        return sortedContractors;
    }

    private List<ContractorShortInfo> loadContractors() {
        return appDatabase.contractorDao().getAll();
    }

    @Override
    public BaseViewHolder<ContractorShortInfo> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.contractor_item, parent, false);
        return new VerticalItemHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<ContractorShortInfo> holder, int position) {
        ContractorShortInfo contractor = contractorList.get(position);
        holder.bind(contractor);
    }

    @Override
    public int getItemCount() {
        return contractorList.size();
    }

    static class VerticalItemHolder extends BaseViewHolder<ContractorShortInfo> {

        private TextView contractorValue;
        private TextView contractorDate;
        private ImageView star;

        public VerticalItemHolder(View itemView) {
            super(itemView);
            contractorValue = itemView.findViewById(R.id.contractor_value);
            contractorDate = itemView.findViewById(R.id.contractor_date);
            star = itemView.findViewById(R.id.star);
        }

        public void bind(ContractorShortInfo contractor) {
            super.bind(contractor);
            contractorValue.setText(contractor.getValue());
            contractorDate.setText(contractor.getLastRequestDate().toString());
            if (contractor.isFavourite()) {
                star.setImageResource(android.R.drawable.star_big_on);
            } else {
                star.setImageResource(android.R.drawable.star_big_off);
            }
        }
    }
}
