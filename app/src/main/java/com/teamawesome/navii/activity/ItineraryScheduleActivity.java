package com.teamawesome.navii.activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.PackageScheduleViewAdapter;
import com.teamawesome.navii.server.model.Attraction;
import com.teamawesome.navii.server.model.Location;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.HeartAndSoulHeaderConfiguration;
import com.teamawesome.navii.util.PackageScheduleAttractionItem;
import com.teamawesome.navii.util.PackageScheduleHeaderItem;
import com.teamawesome.navii.util.PackageScheduleListItem;
import com.teamawesome.navii.util.ToolbarConfiguration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by sjung on 19/06/16.
 */
public class ItineraryScheduleActivity extends NaviiToolbarActivity {

    @BindView(R.id.itinerary_schedule_fab)
    FloatingActionButton mAddScheduleFloatingActionButton;

    @BindView(R.id.itinerary_recycler_view)
    RecyclerView mItineraryRecyclerView;

    @BindView(R.id.menu_gradient)
    RelativeLayout mMenuGradientLayout;

    private PackageScheduleViewAdapter mPackageScheduleViewAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private ItemTouchHelper.Callback mCallback;

    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    public ToolbarConfiguration getToolbarConfiguration() {
        return ToolbarConfiguration.HeartAndSoul;
    }

    @Override
    public void onLeftButtonClick() {
        super.onBackPressed();
    }

    @Override
    public void onRightButtonClick() {
        // Nothing to do here
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        List<Attraction> attractions = getIntent().getParcelableArrayListExtra(Constants.INTENT_ATTRACTION_LIST);
        if (attractions == null) {
            attractions = createAttractionList();
        }
        List<PackageScheduleListItem> items = new ArrayList<>();

        int sectionDivide = (int) Math.ceil((double) attractions.size() / (double) 3);
        int counter = 0;
        for (int i = 0; i < attractions.size(); i++) {
            if (i == (sectionDivide) * counter) {
                items.add(new PackageScheduleHeaderItem(HeartAndSoulHeaderConfiguration.getConfiguration(counter)));
                ++counter;
            }
            items.add(new PackageScheduleAttractionItem(attractions.get(i)));
        }

        mItineraryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPackageScheduleViewAdapter = new PackageScheduleViewAdapter(this, items);
        mPackageScheduleViewAdapter.setSnackbar(mItineraryRecyclerView);
        mItineraryRecyclerView.setAdapter(mPackageScheduleViewAdapter);
        mItemTouchHelper = createItemTouchHelper();
        mItemTouchHelper.attachToRecyclerView(mItineraryRecyclerView);

        setupWindowAnimations();
    }

    @OnTouch(R.id.itinerary_recycler_view)
    public boolean onTouch(MotionEvent event) {
        mPackageScheduleViewAdapter.dismissSnackbar();

        return mItineraryRecyclerView.onTouchEvent(event);
    }

    @OnClick(R.id.itinerary_schedule_fab)
    public void onClick(View view) {
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.d("TAG", place.toString());
                Location location = new Location.Builder()
                        .address(place.getAddress().toString())
                        .latitude(place.getLatLng().latitude)
                        .longitude(place.getLatLng().longitude)
                        .build();


                Attraction attraction = new Attraction.Builder()
                        .location(location)
                        .name(place.getName().toString())
                        .price(place.getPriceLevel())
                        .build();

                mPackageScheduleViewAdapter.add(new PackageScheduleAttractionItem(attraction));

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Snackbar.make(mItineraryRecyclerView, "Cannot Retrieve Search", Snackbar.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                Log.i("Search", "Cancelled");
            }
        }
    }

    @OnClick(R.id.menu_gradient)
    public void touchGradient() {
        if (mMenuGradientLayout.getVisibility() == View.VISIBLE) {
            mMenuGradientLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (mMenuGradientLayout.getVisibility() == View.VISIBLE) {
            mMenuGradientLayout.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }

    public ItemTouchHelper createItemTouchHelper() {
        mCallback = createCallback();
        return new ItemTouchHelper(mCallback);
    }

    public ItemTouchHelper.Callback createCallback() {
        return new ItemTouchHelper.Callback() {

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return mCallback.makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.START | ItemTouchHelper.END);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                mPackageScheduleViewAdapter.move(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (viewHolder == null || viewHolder.getItemViewType() == 1 || direction == ItemTouchHelper.START) {
                    return;
                }
                Log.d("TAG", ""+direction);
                PackageScheduleViewAdapter.PackageItemViewHolder touchVH = (PackageScheduleViewAdapter.PackageItemViewHolder) viewHolder;
                mPackageScheduleViewAdapter.delete(viewHolder.getAdapterPosition());
                touchVH.overlay.setVisibility(View.GONE);
                touchVH.cardView.setVisibility(View.GONE);
                touchVH.relativeLayout.setVisibility(View.GONE);
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (viewHolder == null || viewHolder.getItemViewType() == 1) {
                    return;
                }
                PackageScheduleViewAdapter.PackageItemViewHolder touchVH = (PackageScheduleViewAdapter.PackageItemViewHolder) viewHolder;
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    touchVH.overlay.setVisibility(View.VISIBLE);
                }

                super.onSelectedChanged(viewHolder, actionState);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (viewHolder == null || viewHolder.getItemViewType() == 1 || Math.signum(dX) != 0) {
                    return;
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                        RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                                        boolean isCurrentlyActive) {
                if (viewHolder == null || viewHolder.getItemViewType() == 1 || Math.signum(dY) != 0) {
                    return;
                }

                PackageScheduleViewAdapter.PackageItemViewHolder touchVH =
                        (PackageScheduleViewAdapter.PackageItemViewHolder) viewHolder;

                float leaveBehindLength = touchVH.overlay.getWidth();
                final float dir = Math.signum(dX);

                if (dir == 0) {
                    touchVH.overlay.setTranslationX(-touchVH.itemView.getWidth());
                } else if (dir == 1.0) {
                    touchVH.relativeLayout.setTranslationX(dX);

                    float overlayOffset;
                    float layoutPositionLeft = touchVH.relativeLayout.getTranslationX();

                    if (layoutPositionLeft < leaveBehindLength) {
                        overlayOffset = layoutPositionLeft - leaveBehindLength;
                    } else {
                        overlayOffset = 0.0f;
                    }
                    touchVH.overlay.setTranslationX(overlayOffset);
                }
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                if (viewHolder == null || viewHolder.getItemViewType() == 1) {
                    return;
                }
                PackageScheduleViewAdapter.PackageItemViewHolder touchVH = (PackageScheduleViewAdapter.PackageItemViewHolder) viewHolder;
                touchVH.overlay.setVisibility(View.GONE);

            }
        };
    }

    private List<Attraction> createAttractionList() {
        List<Attraction> attractions = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            attractions.add(
                    new Attraction.Builder()
                            .name("Attraction:" + i)
                            .price(100)
                            .photoUri("http://cpl.jumpfactor.netdna-cdn.com/wp-content/uploads/2015/04/plumber-Toronto-Toronto-plumbers.jpg")
                            .duration(3)
                            .build());
        }
        return attractions;
    }

    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);

        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setReturnTransition(slide);
    }
}
