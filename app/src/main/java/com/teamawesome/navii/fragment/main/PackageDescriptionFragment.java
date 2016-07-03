package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teamawesome.navii.R;
import com.teamawesome.navii.server.model.Attraction;
import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.server.model.Location;

import java.util.List;

/**
 * Created by sjung on 30/01/16.
 */
public class PackageDescriptionFragment extends NaviiFragment implements OnMapReadyCallback,
        AdapterView.OnItemSelectedListener {

    private Itinerary itinerary;
    private ImageView packageImage;
    private TextView packageAuthor;
    private RecyclerView descriptionList;
    private Spinner startTimeSpinner;
    private GoogleMap mMap;
    private static String LOG_TAG = "PackageDescriptionFragment";

    public static PackageDescriptionFragment newInstance(Itinerary itinerary) {
        PackageDescriptionFragment fragment = new PackageDescriptionFragment();
        fragment.setItinerary(itinerary);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        ((MainActivity) parentActivity).setActionBarTitle(getString(R.string.package_description_title));

        //Itinerary description setup
        View view = inflater.inflate(R.layout.fragment_package_description, container, false);

        packageAuthor = (TextView) view.findViewById(R.id.description_author);
        packageImage = (ImageView) view.findViewById(R.id.description_image);

        // TODO: set the background resource
//        packageImage.setBackgroundResource(R.drawable.toronto1);
        packageAuthor.setText(itinerary.getAuthorId());

        //Start Time spinner setup
        startTimeSpinner = (Spinner) view.findViewById(R.id.package_start_time);

        final Integer[] times = {8, 9, 10, 11, 12};

        SpinnerAdapter spinnerAdapter =
                new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, times);

        startTimeSpinner.setAdapter(spinnerAdapter);

        //Attraction list setup
        descriptionList = (RecyclerView) view.findViewById(R.id.description_list);
        descriptionList.setHasFixedSize(true);

        RecyclerView.LayoutManager gridLayoutManager = new LinearLayoutManager(getContext());
        descriptionList.setLayoutManager(gridLayoutManager);

        List<Attraction> attractions = itinerary.getAttractions();

////        PackageScheduleViewAdapter packageScheduleViewAdapter =
////                new PackageScheduleViewAdapter(getContext(), attractions);
//
//        descriptionList.setAdapter(packageScheduleViewAdapter);

        //Map setup
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Hide the zoom controls as the button panel will cover it.
        mMap.getUiSettings().setZoomControlsEnabled(false);
        List<Attraction> attractions = itinerary.getAttractions();

        for (int i = 0; i < attractions.size(); i++) {
            Location location = attractions.get(i).getLocation();

            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(i + ":" + attractions.get(i).getName())
                    .snippet(attractions.get(i).getPhotoUri())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            );
        }

        LatLng toronto = new LatLng(43.644, -79.387);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toronto, 10));
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
