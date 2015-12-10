package com.teamawesome.navii.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamawesome.navii.R;

import java.util.List;

/**
 * Created by sjung on 10/12/15.
 */
public class ItineraryListViewAdapter extends ArrayAdapter<String> {

    private List<String> itineraries;

    public ItineraryListViewAdapter(Context context, int resource, List<String> itineraries) {
        super(context, resource, itineraries);
        this.itineraries = itineraries;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.itinerary_listitem_layout, null);
        }

        TextView textView = (TextView) view.findViewById(R.id.itineraryTitle);
        textView.setText(itineraries.get(position));

        ImageView imageView = (ImageView) view.findViewById(R.id.itineraryBackgroundImage);
        imageView.setImageResource(R.drawable.toronto1);

        return view;
    }
}
