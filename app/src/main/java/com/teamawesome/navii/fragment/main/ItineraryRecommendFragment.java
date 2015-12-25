package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.ItineraryListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjung on 01/12/15.
 */
public class ItineraryRecommendFragment extends NaviiFragment {

    public ItineraryRecommendFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_itinerary_recommend, container, false);
        List<String> values = new ArrayList<>();
        values.add("Package A");
        values.add("Package B");
        ListView listView = (ListView) view.findViewById(R.id.itineraryList);
        ArrayAdapter<String> adapter = new ItineraryListViewAdapter(inflater.getContext(),
                R.layout.itinerary_listitem_layout, values);

        listView.setAdapter(adapter);
        return view;
    }

}
