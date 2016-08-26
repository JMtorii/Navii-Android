package com.teamawesome.navii.fragment.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.ItineraryScheduleActivity;
import com.teamawesome.navii.adapter.PackageScheduleViewAdapter;
import com.teamawesome.navii.server.model.Attraction;
import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.server.model.PackageScheduleListItem;
import com.teamawesome.navii.util.Constants;

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
    private GoogleApiClient mGoogleApiClient;
    private LinearLayoutManager mLayoutManager;
    private ProgressDialog progressDialog;

    private int imageHeight;
    private int imageWidth;

    public List<PackageScheduleListItem> getItems() {
        return mPackageScheduleViewAdapter.getItemsList();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = getContext();
        mGoogleApiClient = new GoogleApiClient
                .Builder(this.getContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(),
                        new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                                Toast.makeText(context, "Could not connect to Google Places API", Toast.LENGTH_SHORT).show();
                            }
                        })
                .build();
    }

    private void getImageViewMetrics() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        imageHeight = (int) getResources().getDimension(R.dimen.heartnsoul_imageview_height);
        imageWidth = metrics.widthPixels;
        Log.d("Width", "w:" + imageWidth + " h:" + imageHeight);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_itinerary_schedule_view, container, false);
        ButterKnife.bind(this, view);
        setExtraFromBundle();
        getImageViewMetrics();
        setPackageScheduleView();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void setPackageScheduleView() {
        List<PackageScheduleListItem> items = itinerary.getPackageScheduleListItems();
        mLayoutManager = new LinearLayoutManager(getActivity());
        mItineraryRecyclerView.setLayoutManager(mLayoutManager);
        mPackageScheduleViewAdapter = new PackageScheduleViewAdapter(getActivity(), items, imageWidth, imageHeight);
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
        if (resultCode == Constants.RESPONSE_ATTRACTION_SELECTED) {
            Attraction attraction = data.getParcelableExtra(Constants.INTENT_ATTRACTION);
            PackageScheduleListItem attractionItem = new PackageScheduleListItem.Builder()
                    .itemType(PackageScheduleListItem.TYPE_ITEM)
                    .attraction(attraction).build();
            mPackageScheduleViewAdapter.add(mLayoutManager.findLastCompletelyVisibleItemPosition(), attractionItem);
        }
        if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
            Status status = PlaceAutocomplete.getStatus(getActivity(), data);
            // TODO: Handle the error.
            Toast.makeText(getContext(), "Cannot Retrieve Search", Toast.LENGTH_SHORT).show();

        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("Search", "Cancelled");
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

    public void add(int i, Attraction attraction) {
        PackageScheduleListItem item = new PackageScheduleListItem.Builder()
                .itemType(PackageScheduleListItem.TYPE_ITEM)
                .attraction(attraction).build();
        mPackageScheduleViewAdapter.add(i, item);
        mLayoutManager.scrollToPositionWithOffset(i, imageHeight);
        Toast.makeText(getContext(), "Added Attraction", Toast.LENGTH_SHORT).show();
    }

    public ItemTouchHelper.Callback createCallback() {
        return new ItemTouchHelper.Callback() {

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return mCallback.makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.END);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                if (viewHolder.getAdapterPosition() > 1 && target.getAdapterPosition() > 1) {
                    mPackageScheduleViewAdapter.move(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (viewHolder == null || viewHolder.getItemViewType() != PackageScheduleListItem.TYPE_ITEM || direction == ItemTouchHelper.START) {
                    return;
                }
                int position = viewHolder.getAdapterPosition();
                final PackageScheduleListItem item = mPackageScheduleViewAdapter.delete(viewHolder.getAdapterPosition());
                showSnackbar(position, item);
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
