package com.teamawesome.navii.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.PackageSelectorViewAdapter;
import com.teamawesome.navii.server.model.Attraction;
import com.teamawesome.navii.util.AnalyticsManager;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.PackageScheduleAttractionItem;
import com.teamawesome.navii.util.PackageScheduleListItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sjung on 05/08/16.
 */
public class PrefetchAttractionsActivity extends Activity {
    @BindView(R.id.prefetch_recycler_view)
    RecyclerView mItineraryRecyclerView;

    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefetch_attractions);
        ButterKnife.bind(this);

        List<Attraction> prefetchedList = getIntent().getParcelableArrayListExtra(Constants.INTENT_EXTRA_RESTAURANT_LIST);
        setupPackageView(prefetchedList);
        AnalyticsManager.getMixpanel().track("PrefetchAttractionsActivity - onCreate");
    }

    private void setupPackageView(List<Attraction> attractions) {
        List<PackageScheduleListItem> items = new ArrayList<>();

        for (int i = 0; i < attractions.size(); i++) {
            items.add(new PackageScheduleAttractionItem(attractions.get(i), 0, 0));
        }
        mItineraryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        PackageSelectorViewAdapter mPackageSelectorViewAdapter = new PackageSelectorViewAdapter(this, items, attractions);
        mItineraryRecyclerView.setAdapter(mPackageSelectorViewAdapter);
    }

    @OnClick(R.id.google_search_bar)
    public void onClick() {
        try {
            LatLng latLng1 = new LatLng(43.636665, -79.399875);
            LatLng latLng2 = new LatLng(43.686420, -79.384329);
            LatLngBounds latLngBounds = new LatLngBounds(latLng1, latLng2);

            AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ESTABLISHMENT)
                    .build();

            Intent intent = new PlaceAutocomplete
                    .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .setFilter(autocompleteFilter)
                    .setBoundsBias(latLngBounds)
                    .build(this);

            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK || resultCode == Constants.RESPONSE_ATTRACTION_SELECTED) {
                setResult(Constants.RESPONSE_GOOGLE_SEARCH, data);
                finish();
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Toast.makeText(this, "Cannot Retrieve Search", Toast.LENGTH_SHORT).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
