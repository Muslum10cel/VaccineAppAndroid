package com.muslumoncel.usermain;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.com.muslumoncel.jsonparseoperations.Baby;
import com.example.muslumoncel.vaccineapp.Options;
import com.example.muslumoncel.vaccineapp.R;

import java.util.List;

/**
 * Created by muslumoncel on 12/02/16.
 */
public class PrivateAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Baby> babies;

    public PrivateAdapter(Activity activity, List<Baby> babies) {
        inflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        this.babies = babies;
    }

    @Override
    public int getCount() {
        return babies.size();
    }

    @Override
    public Object getItem(int position) {
        return babies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.custom_list_view, null);
        TextView textView = (TextView) row.findViewById(R.id.textView);
        ImageView imageView = (ImageView) row.findViewById(R.id.imageView);

        Baby b = babies.get(position);

        textView.setText(b.getBaby_name());
        imageView.setImageResource(R.drawable.arrow);

        return row;
    }
}
