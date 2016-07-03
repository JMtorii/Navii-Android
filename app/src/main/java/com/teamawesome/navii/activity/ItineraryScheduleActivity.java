package com.teamawesome.navii.activity;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.PackageScheduleViewAdapter;
import com.teamawesome.navii.server.model.Attraction;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.PackageScheduleAttractionItem;
import com.teamawesome.navii.util.PackageScheduleHeaderItem;
import com.teamawesome.navii.util.PackageScheduleListItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sjung on 19/06/16.
 */
public class ItineraryScheduleActivity extends Activity {

    @BindView(R.id.itinerary_schedule_fab)
    FloatingActionButton mAddScheduleFloatingActionButton;

    @BindView(R.id.itinerary_recycler_view)
    RecyclerView mItineraryRecyclerView;

    private PackageScheduleViewAdapter mPackageScheduleViewAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private ItemTouchHelper.Callback mCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_schedule);
        ButterKnife.bind(this);

        List<Attraction> attractions = getIntent().getParcelableArrayListExtra(Constants.ATTRACTION_LIST);
        if (attractions == null) {
            attractions = createAttractionList();
        }
        List<PackageScheduleListItem> items = new ArrayList<>();
        String[] sectionsList = getApplicationContext().getResources().getStringArray(R.array.heart_and_soul_schedule_sections);

        int sectionDivide = (int) Math.round((double) attractions.size() / (double) sectionsList.length);
        Log.d("TAG", "Section Divide:" + sectionDivide);
        int counter = 0;
        for (int i = 0; i < attractions.size(); i++) {
            if (i == (sectionDivide) * counter) {
                items.add(new PackageScheduleHeaderItem(sectionsList[counter]));
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
    }

    @OnClick(R.id.itinerary_schedule_fab)
    public void onClick(View view) {
        Snackbar.make(view, "Nope. Not yet.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
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
                if (viewHolder == null || viewHolder.getItemViewType() == 1) {
                    return;
                }
                mPackageScheduleViewAdapter.delete(viewHolder.getAdapterPosition());
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (viewHolder == null || viewHolder.getItemViewType() == 1) {
                    return;
                }

                final PackageScheduleViewAdapter.PackageItemViewHolder touchVH = (PackageScheduleViewAdapter.PackageItemViewHolder) viewHolder;
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    touchVH.overlay.setVisibility(View.VISIBLE);
                }
                Snackbar current = mPackageScheduleViewAdapter.getSnackbar();
                current.dismiss();


                super.onSelectedChanged(viewHolder, actionState);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (viewHolder == null || viewHolder.getItemViewType() == 1) {
                    return;
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                        RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                                        boolean isCurrentlyActive) {
                if (viewHolder == null || viewHolder.getItemViewType() == 1) {
                    return;
                }

                PackageScheduleViewAdapter.PackageItemViewHolder touchVH =
                        (PackageScheduleViewAdapter.PackageItemViewHolder) viewHolder;

                float leaveBehindLength = touchVH.deleteButton.getWidth();
                final float dir = Math.signum(dX);

                touchVH.cardView.setTranslationX(dX);

                if (dir == 0) {
                    touchVH.overlay.setTranslationX(-touchVH.deleteButton.getWidth());
                } else {
                    float overlayOffset;
                    float layoutPositionLeft = touchVH.cardView.getTranslationX();
                    float layoutPositionRight = layoutPositionLeft + touchVH.cardView.getWidth();
                    if (dir == -1.0) {
                        overlayOffset = layoutPositionRight;
                    } else {
                        overlayOffset = layoutPositionLeft - leaveBehindLength;
                    }


                    touchVH.overlay.setTranslationX(overlayOffset);
                }

                float alpha = (float) (1.0 - 1.1 * Math.abs(dX) / viewHolder.itemView.getWidth());
                touchVH.cardView.setAlpha(alpha);

                if (!isCurrentlyActive) {
                    super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
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
                touchVH.cardView.setAlpha(1.0f);
                touchVH.overlay.setVisibility(View.INVISIBLE);

            }
        };
    }

    private List<Attraction> createAttractionList() {
        List<Attraction> attractions = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
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
}
