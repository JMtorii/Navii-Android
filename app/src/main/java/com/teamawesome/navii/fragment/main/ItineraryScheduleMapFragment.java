package com.teamawesome.navii.fragment.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teamawesome.navii.R;
import com.teamawesome.navii.server.model.Attraction;
import com.teamawesome.navii.server.model.Location;
import com.teamawesome.navii.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjung on 22/07/16.
 */
public class ItineraryScheduleMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Itinerary description setup
        View view = inflater.inflate(R.layout.fragment_itinerary_map_view, container, false);

        //Map setup
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.heart_and_soul_map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Hide the zoom controls as the button panel will cover it.
        mMap.getUiSettings().setZoomControlsEnabled(false);

        List<Attraction> attractions = getActivity().getIntent().getParcelableArrayListExtra(Constants.INTENT_ATTRACTION_LIST);
        if (attractions == null) {
            attractions = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                Location location = new Location.Builder()
                        .latitude(43.636665)
                        .longitude(-79.399875)
                        .address("Address")
                        .build();

                Attraction attraction = new Attraction.Builder()
                        .photoUri("http://www.city-data.com/forum/attachments/city-vs-city/105240d1356338901-greater-downtown-toronto-vs-greater-downtown-toronto-skyline-night-view.jpg")
                        .price(2)
                        .location(location)
                        .name("Attraction:" + i)
                        .build();
                attractions.add(attraction);
            }
        }

        for (int i = 0; i < attractions.size(); i++) {
            Log.d("MapFragment", attractions.get(i).getName());
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
        mMap.moveCamera(CameraUpdateFactory.newLatLng(toronto));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                String id = place.getId();
                Log.d("TAG", place.toString());
                Location location = new Location.Builder()
                        .address(place.getAddress().toString())
                        .latitude(place.getLatLng().latitude)
                        .longitude(place.getLatLng().longitude)
                        .build();

                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(place.getName().toString())
                        .snippet(place.getWebsiteUri().toString())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                );
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
//                Snackbar.make(mMap, "Cannot Retrieve Search", Snackbar.LENGTH_SHORT).show();

            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("Search", "Cancelled");
            }
        }
    }
}
