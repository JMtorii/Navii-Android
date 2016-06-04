package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.PackageListViewAdapter;
import com.teamawesome.navii.server.model.Itinerary;

import java.util.List;

/**
 * Created by sjung on 01/12/15.
 */
public class ItineraryRecommendFragment extends NaviiFragment {

    private List<Itinerary> itineraryList;
    private ListView listView;

    public static ItineraryRecommendFragment newInstance(List<Itinerary> itineraryList) {
        ItineraryRecommendFragment itineraryRecommendFragment = new ItineraryRecommendFragment();
        itineraryRecommendFragment.setItineraries(itineraryList);

        return itineraryRecommendFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        ((MainActivity)parentActivity).setActionBarTitle(getString(R.string.we_recommend_title));

        View view = inflater.inflate(R.layout.fragment_itinerary_recommend, container, false);

        listView = (ListView) view.findViewById(R.id.itineraryList);

        PackageListViewAdapter adapter = new PackageListViewAdapter(
                getContext(),
                R.layout.itinerary_listitem_layout,
                itineraryList,
                parentActivity
        );

        listView.setAdapter(adapter);

        return view;
    }

    private void setItineraries(List<Itinerary> itineraryList) {
        this.itineraryList = itineraryList;
    }
}
