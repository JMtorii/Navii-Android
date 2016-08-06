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
import com.teamawesome.navii.server.model.Itinerary;
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
    private List<Itinerary> itineraries;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setExtraFromBundle();

        //Itinerary description setup
        View view = inflater.inflate(R.layout.fragment_itinerary_map_view, container, false);

        //Map setup
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.heart_and_soul_map);
        mapFragment.getMapAsync(this);

        return view;
    }

    private void setExtraFromBundle() {
        Intent intent = getActivity().getIntent();
        itineraries = intent.getParcelableArrayListExtra(Constants.INTENT_ITINERARIES);
    }

    public void updateDay(int position) {
        setMapView(position);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(false);

        // Hide the zoom controls as the button panel will cover it.
        setMapView(0);
        LatLng toronto = new LatLng(43.644, -79.387);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(toronto));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20), 2000, null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.GET_ATTRACTION_EXTRA_REQUEST_CODE) {
            if (resultCode == Constants.RESPONSE_GOOGLE_SEARCH) {
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
            } else if (resultCode == Constants.RESPONSE_ATTRACTION_SELECTED){
                Attraction attraction = data.getParcelableExtra(Constants.INTENT_ATTRACTION);
                Location location = new Location.Builder()
                        .address(attraction.getLocation().getAddress())
                        .latitude(attraction.getLocation().getLatitude())
                        .longitude(attraction.getLocation().getLongitude())
                        .build();

                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(attraction.getName().toString())
                        .snippet(attraction.getPhotoUri())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                );
            } if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
//                Snackbar.make(mMap, "Cannot Retrieve Search", Snackbar.LENGTH_SHORT).show();

            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("Search", "Cancelled");
            }
        }
    }

    public void setMapView(int position) {
        List<Attraction> attractions = itineraries.get(position).getAttractions();
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
                    .title("A")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            );
        }
    }
}
