package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamawesome.navii.R;
import com.teamawesome.navii.server.model.Itinerary;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sjung on 01/12/15.
 */
public class ItineraryRecommendFragment extends NaviiFragment {

    private List<Itinerary> itineraryList;

    @BindView(R.id.itineraryList)
    RecyclerView listView;

    public static ItineraryRecommendFragment newInstance(List<Itinerary> itineraryList) {
        ItineraryRecommendFragment itineraryRecommendFragment = new ItineraryRecommendFragment();
        itineraryRecommendFragment.setItineraries(itineraryList);

        return itineraryRecommendFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        ((MainActivity)parentActivity).setActionBarTitle(getString(R.string.we_recommend_title));
        View view = inflater.inflate(R.layout.fragment_itinerary_recommend, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    private void setItineraries(List<Itinerary> itineraryList) {
        this.itineraryList = itineraryList;
    }
}
