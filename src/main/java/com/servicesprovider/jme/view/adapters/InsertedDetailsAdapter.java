package com.servicesprovider.jme.view.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.servicesprovider.jme.R;
import com.servicesprovider.jme.model.WeightData;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Akash on 3/29/2018.
 */

public class InsertedDetailsAdapter extends RecyclerView.Adapter<InsertedDetailsAdapter.ViewHolder> {

    private Context mContext;
    private List<WeightData> weightDataList;
    int slNo;

    public InsertedDetailsAdapter(Context mContext, List<WeightData> weightDataList) {
        this.mContext = mContext;
        this.weightDataList = weightDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.insert_data_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        WeightData data = weightDataList.get(position);
        Log.d("ComingData",data.getProductVarietyCount()+"\n NetWeight "+data.getNetWeight()+"\n Time "+data.getDateTime());
        if (position%2 == 0)
        {
            holder.item_container.setBackgroundColor(ContextCompat.getColor(mContext,R.color.table_color));
        }
        slNo = position + 1;
//        holder.txt_sl_no.setText(String.valueOf(slNo));
        holder.txt_sl_no.setText(data.getProductVarietyCount());
        holder.txt_time.setText(data.getDateTime());
        holder.txt_no_nets.setText(data.getNumberOfNets());
        holder.txt_total_weight.setText(data.getTotalWeight());
        holder.txt_total_tare_wt.setText(data.getTotalTareWeight());
        holder.txt_net_weight.setText(String.valueOf(data.getNetWeight()));
        holder.txt_cumulative_wt.setText(String.valueOf(data.getComulativeWeight()));

        holder.txt_count.setText(data.getProductVarietyCount());
        holder.txt_count.setVisibility(View.GONE);
        holder.txt_total_net_wt.setVisibility(View.GONE);


    }

    @Override
    public int getItemCount() {
        return weightDataList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {

         @BindView(R.id.item_container)
         LinearLayout item_container;
         @BindView(R.id.txt_sl_no)
         TextView txt_sl_no;
         @BindView(R.id.txt_time)
         TextView txt_time;
         @BindView(R.id.txt_no_nets)
         TextView txt_no_nets;
         @BindView(R.id.txt_total_weight)
         TextView txt_total_weight;
         @BindView(R.id.txt_total_tare_wt)
         TextView txt_total_tare_wt;
         @BindView(R.id.txt_net_weight)
         TextView txt_net_weight;
         @BindView(R.id.txt_cumulative_wt)
         TextView txt_cumulative_wt;
         @BindView(R.id.txt_total_net_wt)
         TextView txt_total_net_wt;
         @BindView(R.id.txt_count)
         TextView txt_count;

         public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
