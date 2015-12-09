package com.teamawesome.navii.fragment.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.teamawesome.navii.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjung on 01/12/15.
 */
public class ItineraryRecommendFragment extends MainFragment {

    public ItineraryRecommendFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_itinerary_recommend, container, false);
        ArrayList<String> values = new ArrayList<>();
        values.add("Package A");
        values.add("Package B");
        ListView listView = (ListView) view.findViewById(R.id.itineraryList);
        ArrayAdapter<String> adapter = new ItineraryListViewAdapter(inflater.getContext(),
                R.layout.itinerary_listitem_layout, values);

        listView.setAdapter(adapter);
        return view;
    }

    private class ItineraryListViewAdapter extends ArrayAdapter<String> {

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
}
