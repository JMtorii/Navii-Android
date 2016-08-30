package com.teamawesome.navii.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.google.maps.android.ui.IconGenerator;
import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.HeartAndSoulDetailsActivity;
import com.teamawesome.navii.activity.ItineraryScheduleActivity;
import com.teamawesome.navii.server.model.Attraction;
import com.teamawesome.navii.server.model.Location;
import com.teamawesome.navii.server.model.PackageScheduleListItem;
import com.teamawesome.navii.util.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sjung on 22/07/16.
 */
public class ItineraryScheduleMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private Map<String, Attraction> markerAttractionMap;
    private static Map<Integer, Integer> markerColorMap;
    static {
        markerColorMap = new HashMap<>();
        markerColorMap.put(1, IconGenerator.STYLE_RED);
        markerColorMap.put(2, IconGenerator.STYLE_BLUE);
        markerColorMap.put(3, IconGenerator.STYLE_GREEN);
        markerColorMap.put(4, IconGenerator.STYLE_PURPLE);
        markerColorMap.put(5, IconGenerator.STYLE_ORANGE);
    }

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
        markerAttractionMap = new HashMap<>();
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(false);

        // Hide the zoom controls as the button panel will cover it.
        setMapView();
    }

    public void setMapView() {
        mMap.clear();
        LatLng toronto = new LatLng(43.644, -79.387);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(toronto));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        ItineraryScheduleActivity activity = (ItineraryScheduleActivity) getActivity();
        List<PackageScheduleListItem> listItems = activity.getPackageScheduleViewAdapter().getItemsList();

        int day = 0;
        int counter = 1;
        for (int i = 0; i < listItems.size(); i++) {
            if (listItems.get(i).getItemType() == PackageScheduleListItem.TYPE_DAY_HEADER) {
                day += 1;
                counter = 1;
            } else if (listItems.get(i).getItemType() == PackageScheduleListItem.TYPE_ITEM) {
                Attraction attraction = listItems.get(i).getAttraction();
                Location location = listItems.get(i).getAttraction().getLocation();
                if (!attraction.getDescription().equals("User Created")) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    String iconString =  String.valueOf(counter);
                    IconGenerator iconFactory = new IconGenerator(getActivity());
                    iconFactory.setStyle(markerColorMap.get(day));
                    MarkerOptions markerOptions = new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(iconString)))
                            .snippet(attraction.getPhotoUri())
                            .title(attraction.getName())
                            .position(latLng)
                            .anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

                    Marker marker = mMap.addMarker(markerOptions);
                    markerAttractionMap.put(marker.getId(), attraction);
                    builder.include(marker.getPosition());
                    counter++;
                }
            }
        }
        if (listItems.size() > 0) {
            LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 45);
            mMap.animateCamera(cu);
        }
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent heartAndSoulDetailsActivity = new Intent(getContext(), HeartAndSoulDetailsActivity.class);
        heartAndSoulDetailsActivity.putExtra(Constants.INTENT_ATTRACTION, markerAttractionMap.get(marker.getId()));
        getActivity().startActivity(heartAndSoulDetailsActivity);
    }

}
