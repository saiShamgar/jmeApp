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
import com.servicesprovider.jme.model.SaveDataSingletone;
import com.servicesprovider.jme.model.WeightData;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Akash on 3/16/2018.
 */

public class WeightDetailsAdapter extends RecyclerView.Adapter<WeightDetailsAdapter.ViewHolder> {

    private Context mContext;
    private List<WeightData> weightList;
    private int slNo ;

    public WeightDetailsAdapter(Context mContext, List<WeightData> weightList) {
        this.mContext = mContext;
        this.weightList = weightList;
    }

    @Override
    public WeightDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.weight_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeightDetailsAdapter.ViewHolder holder, int position) {
        WeightData data = weightList.get(position);

        if (position%2 == 0)
        {
            holder.item_container.setBackgroundColor(Color.LTGRAY);
        }

        // set value in UI
//        Log.d("Position", ""+ ++position);


        if (position%2 == 0)
        {
            holder.item_container.setBackgroundColor(ContextCompat.getColor(mContext,R.color.table_color));
        }

        slNo = position + 1;
        holder.txt_sl_no.setText(""+slNo);
        holder.txt_time.setText(data.getDateTime());
        holder.txt_no_nets.setText(data.getNumberOfNets());
        holder.txt_total_weight.setText(data.getTotalWeight());
        holder.txt_total_tare_wt.setText(data.getTotalTareWeight());
        holder.txt_net_weight.setText(""+data.getNetWeight());

//        SaveDataSingletone.getInstance().setComulativeWeight(data.getComulativeWeight());

//        Log.d("Weight","2: "+SaveDataSingletone.getInstance().getComulativeWeight());

        Log.d("comulativeWeight","2: "+data.getComulativeWeight());

        holder.txt_cumulative_wt.setText(""+data.getComulativeWeight());

    }

    @Override
    public int getItemCount() {
        return weightList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

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

        @BindView(R.id.item_container)
        LinearLayout item_container;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
