package com.servicesprovider.jme.view.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.servicesprovider.jme.R;
import com.servicesprovider.jme.model.HoHlWeightData;
import com.servicesprovider.jme.model.WeightData;
import com.servicesprovider.jme.view.HeadOnHeadLessInsertedDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by muheeb on 10-Apr-18.
 */

public class HoHlInsertedDetailsAdapter extends RecyclerView.Adapter<HoHlInsertedDetailsAdapter.ViewHolder>
{
    Context mContext;
    List<HoHlWeightData> list;
    private int slNo;

    public HoHlInsertedDetailsAdapter(Context mContext, List<HoHlWeightData> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(mContext).inflate(R.layout.hl_ho_insert_details, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        HoHlWeightData data = list.get(position);
        if (position%2 == 0)
        {
            holder.item_container.setBackgroundColor(ContextCompat.getColor(mContext,R.color.table_color));
        }
        slNo = position + 1;
        holder.txt_sl_no.setText(data.getProductVarietyCount());
        holder.txt_time.setText(data.getDateTime());
        holder.txt_no_nets.setText(data.getNumberOfNets());
        holder.txt_total_weight.setText(data.getTotalWeight());
        holder.txt_total_tare_wt.setText(data.getTotalTareWeight());
        holder.txt_net_weight.setText(String.valueOf(data.getNetWeight()));
        holder.txt_cumulative_wt.setText(String.valueOf(data.getComulativeWeight()));
        holder.txt_person.setText(String.valueOf(data.getPersonName()));
        holder.txt_team_no.setText(String.valueOf(data.getTeamNo()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
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
        @BindView(R.id.txt_person_name)
        TextView txt_person;
        @BindView(R.id.txt_team_no)
        TextView txt_team_no;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
