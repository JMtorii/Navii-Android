package com.teamawesome.navii.fragment.main;

import android.app.Activity;
import android.content.Context;
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
import com.teamawesome.navii.server.model.PackageScheduleListItem;

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
    private boolean mEditable;

    private Itinerary itinerary;

    public List<PackageScheduleListItem> getItems() {
        return mPackageScheduleViewAdapter.getItemsList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_itinerary_schedule_view, container, false);
        ButterKnife.bind(this, view);

        setExtraFromBundle();
        setPackageScheduleView();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void setPackageScheduleView() {
        List<PackageScheduleListItem> items = itinerary.getPackageScheduleListItems();

        mItineraryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPackageScheduleViewAdapter = new PackageScheduleViewAdapter(getActivity(), items);
        mItineraryRecyclerView.setAdapter(mPackageScheduleViewAdapter);

        mItemTouchHelper = createItemTouchHelper();
        mItemTouchHelper.attachToRecyclerView(mItineraryRecyclerView);
    }

    private void setExtraFromBundle() {
        ItineraryScheduleActivity activity = (ItineraryScheduleActivity) getActivity();
        itinerary = activity.getItinerary();
        mEditable = activity.getIntent().getBooleanExtra(Constants.INTENT_ITINERARY_EDITABLE, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSnackbar = Snackbar.make(mItineraryRecyclerView, "Done with this?", Snackbar.LENGTH_INDEFINITE);
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

                PackageScheduleListItem attractionItem = new PackageScheduleListItem.Builder()
                        .itemType(4)
                        .attraction(attraction).build();
//                attractionItem.setBitmap(image);
                mPackageScheduleViewAdapter.add(attractionItem);
            } else if (resultCode == Constants.RESPONSE_ATTRACTION_SELECTED) {
                Attraction attraction = data.getParcelableExtra(Constants.INTENT_ATTRACTION);
                PackageScheduleListItem attractionItem = new PackageScheduleListItem.Builder()
                        .itemType(4)
                        .attraction(attraction).build();
                mPackageScheduleViewAdapter.add(attractionItem);
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

    public void showSnackbar(final int position, final PackageScheduleListItem item) {
        mSnackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPackageScheduleViewAdapter.add(position, item);
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
                if (viewHolder == null || viewHolder.getItemViewType() != PackageScheduleListItem.TYPE_ITEM || direction == ItemTouchHelper.START) {
                    return;
                }
                final PackageScheduleViewAdapter.PackageItemViewHolder touchVH = (PackageScheduleViewAdapter.PackageItemViewHolder) viewHolder;
                final int position = touchVH.getAdapterPosition();
                Log.d("TAG", "" + position);
                final PackageScheduleListItem item = mPackageScheduleViewAdapter.delete(viewHolder.getAdapterPosition());
                showSnackbar(viewHolder.getAdapterPosition(), item);
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (viewHolder == null || viewHolder.getItemViewType() != PackageScheduleListItem.TYPE_ITEM) {
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
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (viewHolder == null || viewHolder.getItemViewType() != PackageScheduleListItem.TYPE_ITEM || Math.signum(dY) != 0) {
                    return;
                }

                PackageScheduleViewAdapter.PackageItemViewHolder touchVH = (PackageScheduleViewAdapter.PackageItemViewHolder) viewHolder;

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
                return true;
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return mEditable;
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

                super.clearView(recyclerView, viewHolder);

                if (viewHolder != null && viewHolder.getItemViewType() == PackageScheduleListItem.TYPE_ITEM) {
                    PackageScheduleViewAdapter.PackageItemViewHolder touchVH = (PackageScheduleViewAdapter.PackageItemViewHolder) viewHolder;
                    touchVH.overlay.setVisibility(View.GONE);
                    touchVH.itemView.setAlpha(1.0f);
                }
            }
        };

    }
}
