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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.ItineraryScheduleActivity;
import com.teamawesome.navii.server.model.Attraction;
import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.server.model.Location;
import com.teamawesome.navii.server.model.PackageScheduleListItem;
import com.teamawesome.navii.util.Constants;

import java.util.List;

/**
 * Created by sjung on 22/07/16.
 */
public class ItineraryScheduleMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private Itinerary itineraries;

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
        ItineraryScheduleActivity activity = (ItineraryScheduleActivity) getActivity();
        itineraries = activity.getItinerary();
    }

    public void updateDay() {
        setMapView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(false);

        // Hide the zoom controls as the button panel will cover it.
        setMapView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.GET_ATTRACTION_EXTRA_REQUEST_CODE) {
            if (resultCode == Constants.RESPONSE_GOOGLE_SEARCH) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                Log.d("TAG", place.toString());
                mMap.addMarker(new MarkerOptions()
                        .position(place.getLatLng())
                        .title(place.getName().toString())
                        .snippet(place.getWebsiteUri() != null ? place.getWebsiteUri().toString() : "No website")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                );
            } else if (resultCode == Constants.RESPONSE_ATTRACTION_SELECTED) {
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
            }
            if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
//                Snackbar.make(mMap, "Cannot Retrieve Search", Snackbar.LENGTH_SHORT).show();

            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("Search", "Cancelled");
            }
        }
    }


    public void setMapView() {
        mMap.clear();
        LatLng toronto = new LatLng(43.644, -79.387);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(toronto));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();


        List<PackageScheduleListItem> listItems = itineraries.getPackageScheduleListItems();

        int dayCounter = 0;
        for (int i = 0; i < listItems.size(); i++) {
            if (listItems.get(i).getItemType() == PackageScheduleListItem.TYPE_DAY_HEADER) {
                ++dayCounter;
            } else if (listItems.get(i).getItemType() == PackageScheduleListItem.TYPE_ITEM) {
                Attraction attraction = listItems.get(i).getAttraction();
                Location location = listItems.get(i).getAttraction().getLocation();
                if (!attraction.getDescription().equals("User Created")) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(i + ":" + attraction.getName())
                            .snippet(attraction.getPhotoUri())
                            .title(attraction.getName())
                            .icon(BitmapDescriptorFactory.defaultMarker(dayCounter * 30.0F)));
                    builder.include(marker.getPosition());
                }
            }
        }
        if (listItems.size() > 0) {
            LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 20);
            mMap.animateCamera(cu);
        }
    }
}
