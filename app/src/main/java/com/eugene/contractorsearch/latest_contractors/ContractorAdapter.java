package com.eugene.contractorsearch.latest_contractors;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eugene.contractorsearch.R;
import com.eugene.contractorsearch.model.Contractor;

import java.util.ArrayList;
import java.util.List;

public class ContractorAdapter extends RecyclerView.Adapter<BaseViewHolder<Contractor>> {

    private List<Contractor> contractorList;

    public ContractorAdapter() {
        contractorList = new ArrayList<>();
    }

    public void setDataFromDb() {}

    @Override
    public BaseViewHolder<Contractor> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.contractor_item, parent, false);
        return new VerticalItemHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<Contractor> holder, int position) {
        Contractor contractor = contractorList.get(position);
        holder.bind(contractor);
    }

    @Override
    public int getItemCount() {
        return contractorList.size();
    }

    static class VerticalItemHolder extends BaseViewHolder<Contractor> {

        private TextView contractorValue;

        public VerticalItemHolder(View itemView) {
            super(itemView);
            contractorValue = itemView.findViewById(R.id.contractor_value);
        }

        public void bind(Contractor contractor) {
            super.bind(contractor);
            contractorValue.setText(contractor.getValue());
        }
    }
}
