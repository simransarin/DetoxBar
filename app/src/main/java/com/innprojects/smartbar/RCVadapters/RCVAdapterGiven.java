package com.innprojects.smartbar.RCVadapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.innprojects.smartbar.Models.Flavour;
import com.innprojects.smartbar.R;
import java.util.ArrayList;

/**
 * Created by simransarin on 07/10/17.
 */

public class RCVAdapterGiven extends RecyclerView.Adapter<RCVAdapterGiven.OurHolder> {

    PlusMinusClickedListener listener;
    private Context mContext;
    private ArrayList<Flavour> flavours;

    public RCVAdapterGiven(Context context, ArrayList<Flavour> data, PlusMinusClickedListener listener) {
        mContext = context;
        flavours = data;
        this.listener = listener;
    }

    @Override
    public RCVAdapterGiven.OurHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_itemgiven, parent, false);
        return new OurHolder(v);
    }

    @Override
    public void onBindViewHolder(final RCVAdapterGiven.OurHolder holder, final int position) {
        final Flavour f = flavours.get(position);
        String id = f.getId();
        holder.tv1.setText(f.getIngredient_name());
            holder.tv2.setText(f.getQuantity());
        if (Integer.parseInt(holder.tv2.getText().toString()) <= 100) {
            holder.ll.setAlpha(0.3f);
            holder.tv1.setAlpha(0.3f);
            holder.tv2.setAlpha(0.3f);
            holder.tv3.setAlpha(0.3f);
            holder.tv4.setAlpha(0.3f);
            holder.et.setAlpha(0.3f);
            holder.b1.setAlpha(0.3f);
            holder.b2.setAlpha(0.3f);
            holder.b1.setEnabled(false);
            holder.b2.setEnabled(false);
        } else if (Integer.parseInt(holder.et.getText().toString()) == 0) {
            holder.b2.setAlpha(0.3f);
            holder.b2.setEnabled(false);
        }
        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ml = holder.et.getText().toString();
                int mlint = 0;
                int mGiven = Integer.parseInt(holder.tv2.getText().toString());
                if (mGiven >= 105) {
                    holder.b1.setAlpha(1f);
                    holder.b2.setAlpha(1f);
                    holder.b1.setEnabled(true);
                    holder.b2.setEnabled(true);
                    mlint = Integer.parseInt(ml);
                    mlint += 10;
                    mGiven -= 10;
                    holder.et.setText(Integer.toString(mlint));
                    holder.tv2.setText(Integer.toString(mGiven));
                    f.setQuantity_selected(Integer.toString(mlint));
                    f.setQuantity(Integer.toString(mGiven));
                    listener.plusClicked(f, Integer.toString(mlint));
                } else {
                    holder.b1.setAlpha(0.3f);
                    holder.b1.setEnabled(false);
                }
            }
        });
        holder.b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ml = holder.et.getText().toString();
                int mlint = 0;
                int mGiven = Integer.parseInt(holder.tv2.getText().toString());
                if (mGiven >= 100
                        && Integer.parseInt(ml) > 0) {
                    holder.b1.setAlpha(1f);
                    holder.b2.setAlpha(1f);
                    holder.b1.setEnabled(true);
                    holder.b2.setEnabled(true);
                    mlint = Integer.parseInt(ml);
                    mlint -= 10;
                    mGiven += 10;
                    holder.et.setText(Integer.toString(mlint));
                    holder.tv2.setText(Integer.toString(mGiven));
                    f.setQuantity(Integer.toString(mGiven));
                    f.setQuantity_selected(Integer.toString(mlint));
                    listener.minusClicked(f, Integer.toString(mlint));
                } else {
                    holder.b2.setAlpha(0.3f);
                    holder.b2.setEnabled(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return flavours.size();
    }

    public interface PlusMinusClickedListener {
        void plusClicked(Flavour f, String quant);

        void minusClicked(Flavour f, String quant);
    }

    public class OurHolder extends RecyclerView.ViewHolder {
        LinearLayout ll;
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        ImageButton b1;
        ImageButton b2;
        TextView et;

        public OurHolder(View itemView) {
            super(itemView);
            ll = (LinearLayout) itemView.findViewById(R.id.ll);
            tv1 = (TextView) itemView.findViewById(R.id.flavour_name);
            tv2 = (TextView) itemView.findViewById(R.id.flavour_ml);
            tv3 = (TextView) itemView.findViewById(R.id.ml_textview);
            tv4 = (TextView) itemView.findViewById(R.id.ml_textv);
            b1 = (ImageButton) itemView.findViewById(R.id.plus);
            b2 = (ImageButton) itemView.findViewById(R.id.minus);
            et = (TextView) itemView.findViewById(R.id.selected_ml);
        }
    }
}
