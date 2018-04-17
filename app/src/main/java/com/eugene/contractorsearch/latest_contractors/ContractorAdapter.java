package com.eugene.contractorsearch.latest_contractors;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.eugene.contractorsearch.App;
import com.eugene.contractorsearch.R;
import com.eugene.contractorsearch.contractor_info.ContractorInfoActivity;
import com.eugene.contractorsearch.db.AppDatabase;
import com.eugene.contractorsearch.db.ContractorShortInfo;
import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContractorAdapter extends RecyclerView.Adapter<BaseViewHolder<ContractorShortInfo>>
        implements Filterable {

    private List<ContractorShortInfo> contractorList;
    private List<ContractorShortInfo> contractorListFiltered;
    private AppDatabase appDatabase;

    public ContractorAdapter() {
        contractorList = new ArrayList<>();
        contractorListFiltered = new ArrayList<>();
        appDatabase = App.getInstance().getAppDatabase();
    }

    public void setDataFromDb() {
        contractorList.clear();
        contractorListFiltered.clear();
        Single.fromCallable(this::loadContractors)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contractorShortInfoList -> {
                    contractorList.addAll(sortContractors(contractorShortInfoList));
                    contractorListFiltered.addAll(contractorList);
                    notifyDataSetChanged();
                });
    }

    private List<ContractorShortInfo> sortContractors(List<ContractorShortInfo> contractorList) {
        List<ContractorShortInfo> favoriteContractors = new ArrayList<>(Collections2
                .filter(contractorList, contractorShortInfo -> contractorShortInfo != null &&
                        contractorShortInfo.isFavourite()));
        List<ContractorShortInfo> notFavoriteContractors = new ArrayList<>(Collections2
                .filter(contractorList, contractorShortInfo -> contractorShortInfo != null &&
                        !contractorShortInfo.isFavourite()));
        Collections.sort(favoriteContractors, (contractor1, contractor2) ->
                contractor2.getLastRequestDate().compareTo(contractor1.getLastRequestDate()));
        Collections.sort(notFavoriteContractors, (contractor1, contractor2) ->
                contractor2.getLastRequestDate().compareTo(contractor1.getLastRequestDate()));
        favoriteContractors.addAll(notFavoriteContractors);
        return favoriteContractors;
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
        ContractorShortInfo contractor = contractorListFiltered.get(position);
        holder.bind(contractor);
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(ContractorInfoActivity.CONTRACTOR_ID, contractor.getHid());
            bundle.putBoolean(ContractorInfoActivity.IS_NEED_TO_REFRESH, true);
            Intent intent = new Intent(v.getContext(), ContractorInfoActivity.class);
            intent.putExtras(bundle);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return contractorListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                contractorListFiltered.clear();
                if (charString.isEmpty()) {
                    contractorListFiltered.addAll(contractorList);
                } else {
                    List<ContractorShortInfo> filteredList = new ArrayList<>(Collections2
                            .filter(contractorList, contractorShortInfo -> contractorShortInfo != null &&
                                    contractorShortInfo.getValue().toLowerCase().contains(charString.toLowerCase())));
                    contractorListFiltered.addAll(filteredList);
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = contractorListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                contractorListFiltered = (ArrayList<ContractorShortInfo>) results.values;
                notifyDataSetChanged();
            }
        };
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
