package com.example.muslumoncel.vaccineapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 *
 * @author muslumoncel
 */


public class PrivateAdapter extends BaseAdapter {

	private final LayoutInflater layoutInflater;
	private final List<Options> seceneks;
	
	public PrivateAdapter(Activity activity, List<Options> option) {
		//XML'i alıp View'a çevirecek inflater'ı örnekleyelim
		layoutInflater = (LayoutInflater) activity.getSystemService(
			Context.LAYOUT_INFLATER_SERVICE);
		//gösterilecek listeyi de alalım
		seceneks = option;
	}
	
	public int getCount() {
		return seceneks.size();
	}

	public Object getItem(int position) {
		return seceneks.get(position);
	}

	public long getItemId(int position) {
		return position; 
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row;
		
		row=layoutInflater.inflate(R.layout.custom_list_view, null);
		TextView textView = (TextView) row.findViewById(R.id.textView);
		ImageView imageView = (ImageView) row.findViewById(R.id.imageView);
		
		Options s=seceneks.get(position);
		
		textView.setText(s.getOption());
		imageView.setImageResource(R.drawable.arrow);
		
		return row;
	}	
}
