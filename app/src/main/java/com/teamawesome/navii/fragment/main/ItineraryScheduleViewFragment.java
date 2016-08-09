package com.teamawesome.navii.fragment.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.ItineraryScheduleActivity;
import com.teamawesome.navii.adapter.PackageScheduleViewAdapter;
import com.teamawesome.navii.server.model.Attraction;
import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.server.model.Location;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.HeartAndSoulHeaderConfiguration;
import com.teamawesome.navii.util.PackageScheduleAttractionItem;
import com.teamawesome.navii.util.PackageScheduleHeaderItem;
import com.teamawesome.navii.util.PackageScheduleListItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

/**
 * Created by sjung on 22/07/16.
 */
public class ItineraryScheduleViewFragment extends Fragment {

    @BindView(R.id.itinerary_recycler_view)
    RecyclerView mItineraryRecyclerView;

    private PackageScheduleViewAdapter mPackageScheduleViewAdapter;

    private ItemTouchHelper mItemTouchHelper;
    private ItemTouchHelper.Callback mCallback;
    private Snackbar mSnackbar;
    private int spinnerPosition;
    private boolean mEditable;

    private List<Itinerary> itineraries;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_itinerary_schedule_view, container, false);
        ButterKnife.bind(this, view);

        setExtraFromBundle();
        spinnerPosition = 0;
        setPackageScheduleView(spinnerPosition);
        return view;
    }

    private void setPackageScheduleView(int position) {
        List<Attraction> attractions = itineraries.get(position).getAttractions();
        List<PackageScheduleListItem> items = new ArrayList<>();

        if (attractions == null) {
            attractions = new ArrayList<>();
        }

        int sectionDivide = (int) Math.ceil((double) attractions.size() / (double) 3);
        int counter = 0;
        for (int i = 0; i < attractions.size(); i++) {
            if (i == (sectionDivide) * counter) {
                items.add(new PackageScheduleHeaderItem(HeartAndSoulHeaderConfiguration.getConfiguration(counter)));
                ++counter;
            }
            items.add(new PackageScheduleAttractionItem(attractions.get(i)));
        }

        mItineraryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPackageScheduleViewAdapter = new PackageScheduleViewAdapter(getActivity(), items);
        mItineraryRecyclerView.setAdapter(mPackageScheduleViewAdapter);

        mItemTouchHelper = createItemTouchHelper();
        mItemTouchHelper.attachToRecyclerView(mItineraryRecyclerView);
    }

    private void setExtraFromBundle() {
        ItineraryScheduleActivity activity = (ItineraryScheduleActivity) getActivity();
        itineraries = activity.getItineraries();
        mEditable = activity.getIntent().getBooleanExtra(Constants.INTENT_ITINERARY_EDITABLE, false);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSnackbar = Snackbar.make(mItineraryRecyclerView, "Done with this?", Snackbar.LENGTH_INDEFINITE);
    }

    public void updateDay(int position) {
        List<Attraction> attractions = itineraries.get(position).getAttractions();
        List<PackageScheduleListItem> items = new ArrayList<>();

        if (attractions == null) {
            attractions = new ArrayList<>();
        }

        int sectionDivide = (int) Math.ceil((double) attractions.size() / (double) 3);
        int counter = 0;
        for (int i = 0; i < attractions.size(); i++) {
            if (i == (sectionDivide) * counter) {
                items.add(new PackageScheduleHeaderItem(HeartAndSoulHeaderConfiguration.getConfiguration(counter)));
                ++counter;
            }
            items.add(new PackageScheduleAttractionItem(attractions.get(i)));
        }

        mPackageScheduleViewAdapter.replace(items);
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

                Attraction attraction = new Attraction.Builder()
                        .location(location)
                        .name(place.getName().toString())
                        .price(place.getPriceLevel())
                        .build();

                Bitmap image = null;
//                try {
//                    image = new PhotoTask(getContext()) {
//                        @Override
//                        protected void onPreExecute() {
//                            // Display a temporary image to show while bitmap is loading.
//                        }
//
//                        @Override
//                        protected void onPostExecute(Bitmap attributedPhoto) {
//                            progressDialog.dismiss();
//                        }
//                    }.execute(id).get(20000, TimeUnit.MILLISECONDS);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (TimeoutException e) {
//                    e.printStackTrace();
//                }

                PackageScheduleAttractionItem attractionItem = new PackageScheduleAttractionItem(attraction);
                attractionItem.setBitmap(image);
                mPackageScheduleViewAdapter.add(attractionItem);
                itineraries.get(spinnerPosition).getAttractions().add(attraction);
            } else if (resultCode == Constants.RESPONSE_ATTRACTION_SELECTED) {
                Attraction attraction = data.getParcelableExtra(Constants.INTENT_ATTRACTION);
                mPackageScheduleViewAdapter.add(new PackageScheduleAttractionItem(attraction));
                itineraries.get(spinnerPosition).getAttractions().add(attraction);
            }
            if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Snackbar.make(mItineraryRecyclerView, "Cannot Retrieve Search", Snackbar.LENGTH_SHORT).show();

            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("Search", "Cancelled");
            }
        }
    }

    @OnTouch(R.id.itinerary_recycler_view)
    public boolean onTouch(MotionEvent event) {
        if (mSnackbar != null && mSnackbar.isShown()) {
            mSnackbar.dismiss();
        }
        return mItineraryRecyclerView.onTouchEvent(event);
    }

    public ItemTouchHelper createItemTouchHelper() {
        mCallback = createCallback();
        return new ItemTouchHelper(mCallback);
    }

    public void showSnackbar(final int position, final PackageScheduleListItem item, final Attraction attraction, final int listPosition) {
        mSnackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPackageScheduleViewAdapter.add(position, item);
                itineraries.get(spinnerPosition).getAttractions().add(listPosition, attraction);
            }
        }).show();
    }

    public ItemTouchHelper.Callback createCallback() {
        return new ItemTouchHelper.Callback() {

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return mCallback.makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.END);
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
                final PackageScheduleViewAdapter.PackageItemViewHolder touchVH = (PackageScheduleViewAdapter.PackageItemViewHolder) viewHolder;
                final int position = touchVH.getAdapterPosition();
                Log.d("TAG", "" + position);
                final PackageScheduleListItem item = mPackageScheduleViewAdapter.delete(viewHolder.getAdapterPosition());
                Attraction attraction = ((PackageScheduleAttractionItem) item).getAttraction();
                List<Attraction> list = itineraries.get(spinnerPosition).getAttractions();
                int listPosition = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).equals(attraction)) {
                        list.remove(i);
                        listPosition = i;
                        break;
                    }
                }
                itineraries.get(spinnerPosition).getAttractions();
                showSnackbar(position, item, attraction, listPosition);
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (viewHolder == null || viewHolder.getItemViewType() == 1) {
                    return;
                }

                PackageScheduleViewAdapter.PackageItemViewHolder touchVH = (PackageScheduleViewAdapter.PackageItemViewHolder) viewHolder;

                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    touchVH.itemView.setAlpha(0.7f);
                } else if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    touchVH.overlay.setVisibility(View.VISIBLE);
                    touchVH.itemView.setAlpha(1.0f);
                } else {
                    touchVH.overlay.setVisibility(View.INVISIBLE);
                    touchVH.itemView.setAlpha(1.0f);
                }

                super.onSelectedChanged(viewHolder, actionState);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                                    boolean isCurrentlyActive) {
                if (Math.abs(dY) > 0.0f && dX == 0.0f) {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
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

                if (Math.abs(dY) > 0 && dir == 0) {
                    super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    return;
                }

                if (dX == 0) {
                    touchVH.relativeLayout.setTranslationX(0.0f);
//                    touchVH.overlay.setTranslationX(-touchVH.itemView.getWidth());
                } else if (dX > 0) {
                    touchVH.relativeLayout.setTranslationX(dX);

                    float overlayOffset;
                    float layoutPositionLeft = touchVH.relativeLayout.getTranslationX();

                    if (layoutPositionLeft < leaveBehindLength) {
                        overlayOffset = layoutPositionLeft - leaveBehindLength;
                    } else {
                        overlayOffset = 0.0f;
                    }
                    touchVH.overlay.setTranslationX(overlayOffset);
                } else {
                    touchVH.relativeLayout.setTranslationX(0.0f);
                }
                Log.d("OnChildDrawOver", "dX:" + dX + " actionState:" + actionState + " isCurrentlyActive:" + isCurrentlyActive);
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return mEditable;
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return mEditable;
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

                super.clearView(recyclerView, viewHolder);

                if (viewHolder != null && viewHolder.getItemViewType() == 0) {
                    PackageScheduleViewAdapter.PackageItemViewHolder touchVH = (PackageScheduleViewAdapter.PackageItemViewHolder) viewHolder;
                    touchVH.overlay.setVisibility(View.GONE);
                    touchVH.itemView.setAlpha(1.0f);
                }
            }
        };

    }
}
