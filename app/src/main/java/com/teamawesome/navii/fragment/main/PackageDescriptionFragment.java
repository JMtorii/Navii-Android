package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.MainActivity;
import com.teamawesome.navii.adapter.DescriptionListAdapter;
import com.teamawesome.navii.server.model.Itinerary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjung on 30/01/16.
 */
public class PackageDescriptionFragment extends NaviiFragment {

    private Itinerary itinerary;
    private ImageView packageImage;
    private TextView packageTitle;
    private TextView packageAuthor;
    private RecyclerView descriptionList;

    public static PackageDescriptionFragment newInstance(Itinerary itinerary) {
        PackageDescriptionFragment fragment = new PackageDescriptionFragment();
        fragment.setItinerary(itinerary);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity)parentActivity).setActionBarTitle(getString(R.string.package_description_title));

        View view = inflater.inflate(R.layout.fragment_package_description, container, false);

        packageAuthor = (TextView) view.findViewById(R.id.description_author);
        packageImage = (ImageView) view.findViewById(R.id.description_image);
        packageTitle = (TextView) view.findViewById(R.id.description_title);

        packageAuthor.setText(itinerary.getAuthorId());
        packageTitle.setText(itinerary.getDescription());
        packageImage.setBackgroundResource(R.drawable.toronto1);

        descriptionList = (RecyclerView) view.findViewById(R.id.description_list);
        descriptionList.setHasFixedSize(true);

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        descriptionList.setLayoutManager(gridLayoutManager);

        //TODO: Change to actual attraction pictures
        List<String> items = new ArrayList<>();
        items.add("Moon");
        items.add("TD Bank Attraction");
        items.add("Restaurant Attraction of pain");
        items.add("Club Attraction of pain");
        items.add("Something pain");
        items.add("Oh my god");
        items.add("Church visit");
        items.add("Book Burning");
        items.add("Prayer");

        DescriptionListAdapter descriptionListAdapter =
                new DescriptionListAdapter(getContext(), items);

        descriptionList.setAdapter(descriptionListAdapter);

        return view;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

}
