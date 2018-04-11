package com.innprojects.smartbar.RCVadapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innprojects.smartbar.Models.Flavour;
import com.innprojects.smartbar.R;

import java.util.ArrayList;

/**
 * Created by simransarin on 07/10/17.
 */

public class RCVAdapterFinal extends RecyclerView.Adapter<RCVAdapterFinal.OurHolder> {

    private Context mContext;
    private ArrayList<Flavour> flavours;

    public RCVAdapterFinal(Context context, ArrayList<Flavour> data) {
        mContext = context;
        flavours = data;
    }

    public class OurHolder extends RecyclerView.ViewHolder{

        TextView tv1;
        TextView tv2;
        TextView tv3;

        public OurHolder(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.flavour_name);
            tv2 = (TextView) itemView.findViewById(R.id.flavour_ml);
        }
    }
    @Override
    public RCVAdapterFinal.OurHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_itemfinal,parent,false);
        return new OurHolder(v);
    }

    @Override
    public void onBindViewHolder(RCVAdapterFinal.OurHolder holder, final int position) {
        final Flavour b = flavours.get(position);
        String id = b.getId();
        holder.tv1.setText(b.getIngredient_name());
        holder.tv2.setText(b.getQuantity_selected());
    }

    @Override
    public int getItemCount() {
        return flavours.size();
    }
}
