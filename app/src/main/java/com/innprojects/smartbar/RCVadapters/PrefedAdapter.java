package com.innprojects.smartbar.RCVadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.innprojects.smartbar.Models.Prefed;
import com.innprojects.smartbar.R;
import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by simra on 20-06-2016.
 */
public class PrefedAdapter extends ArrayAdapter<Prefed> {
    Context context;
    ArrayList<Prefed> arrayList = new ArrayList<Prefed>();

    public PrefedAdapter(Context context, ArrayList<Prefed> objects) {
        super(context, 0, objects);
        arrayList = objects;
        this.context=context;
    }

    private class PreFedHolder{
        TextView PreFedImageView;
        TextView PreFedTextView;

        public PreFedHolder(TextView PreFedImageView, TextView PreFedTextView) {
            this.PreFedImageView = PreFedImageView;
            this.PreFedTextView = PreFedTextView;
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.prefed_drinks, parent, false);
            TextView PreFedImageView = view.findViewById(R.id.preFed);
            TextView PreFedTextView = view.findViewById(R.id.text);
            PreFedHolder holder = new PreFedHolder(PreFedImageView, PreFedTextView);
            view.setTag(holder);
        }
        PreFedHolder holder = (PreFedHolder) view.getTag();
        Prefed currentPreFed = arrayList.get(position);
        int color = 0x00FFF55E4E;
        if (currentPreFed.getColour().equals("red")) {
            color = 0xFFffc107;
        } else if(currentPreFed.getColour().equals("blue")){
            color = 0xFFFF7F50;
        }
        holder.PreFedImageView.setBackgroundColor(color);
        holder.PreFedTextView.setText(currentPreFed.getName());
        holder.PreFedImageView.setText(Character.toString(currentPreFed.getName().charAt(0)));
        return view;
    }
}
