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
import com.servicesprovider.jme.model.HoHlWeightData;
import com.servicesprovider.jme.model.WeightData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by muheeb on 10-Apr-18.
 */

public class HoHlWeightDetailsAdapter extends RecyclerView.Adapter<HoHlWeightDetailsAdapter.ViewHolder>
{
    Context mContext;
    List<HoHlWeightData> weightData;
    public HoHlWeightDetailsAdapter(Context mContext, List<HoHlWeightData> weightData)
    {
        this.mContext = mContext;
        this.weightData = weightData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.ho_hl_weight_row,parent,false);

        return new HoHlWeightDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        HoHlWeightData data = weightData.get(position);

        if (position%2 == 0)
        {
            holder.item_container.setBackgroundColor(ContextCompat.getColor(mContext,R.color.table_color));
        }


        holder.txt_time.setText(data.getDateTime());
        holder.txt_no_nets.setText(data.getNumberOfNets());
        holder.txt_total_weight.setText(data.getTotalWeight());
        holder.txt_total_tare_wt.setText(data.getTotalTareWeight());
        holder.txt_net_weight.setText(""+data.getNetWeight());
        holder.txt_person_name.setText(data.getPersonName());
        holder.txt_team_no.setText(data.getTeamNo());

//        SaveDataSingletone.getInstance().setComulativeWeight(data.getComulativeWeight());

//        Log.d("Weight","2: "+SaveDataSingletone.getInstance().getComulativeWeight());

        Log.d("comulativeWeight","2: "+data.getComulativeWeight());

        holder.txt_cumulative_wt.setText(""+data.getComulativeWeight());
    }

    @Override
    public int getItemCount() {
        return weightData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
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
        @BindView(R.id.txt_group_person_name)
        TextView txt_person_name;
        @BindView(R.id.txt_team_no)
        TextView txt_team_no;

        @BindView(R.id.item_container)
        LinearLayout item_container;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
