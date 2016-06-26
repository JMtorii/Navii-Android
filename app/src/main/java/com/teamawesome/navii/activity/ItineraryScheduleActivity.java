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
import com.teamawesome.navii.adapter.DescriptionListAdapter;
import com.teamawesome.navii.server.model.Attraction;
import com.teamawesome.navii.util.Constants;

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

    private DescriptionListAdapter mDescriptionListAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private ItemTouchHelper.Callback mCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_schedule);
        ButterKnife.bind(this);

        List<Attraction> attractions =
                getIntent().getParcelableArrayListExtra(Constants.ATTRACTION_LIST);
        if (attractions == null) {
            attractions = CreateAttractionList();
        }
        mItineraryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDescriptionListAdapter = new DescriptionListAdapter(this, attractions);
        mItineraryRecyclerView.setAdapter(mDescriptionListAdapter);
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
            public int getMovementFlags(RecyclerView recyclerView,
                                        RecyclerView.ViewHolder viewHolder) {
                return mCallback.makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.START | ItemTouchHelper.END);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                Log.d("TAG", "onMove:" + viewHolder.getAdapterPosition());
                DescriptionListAdapter.PackageViewHolder touchVH = (DescriptionListAdapter.PackageViewHolder) viewHolder;

                mDescriptionListAdapter.move(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                DescriptionListAdapter descriptionListAdapter = (DescriptionListAdapter) recyclerView.getAdapter();

                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mDescriptionListAdapter.delete(viewHolder.getAdapterPosition());
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                Log.d("TAG", "onSelectedChanged");

                super.onSelectedChanged(viewHolder, actionState);
                final DescriptionListAdapter.PackageViewHolder touchVH = (DescriptionListAdapter.PackageViewHolder) viewHolder;
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    touchVH.overlay.setTranslationX(viewHolder.itemView.getWidth());
                    touchVH.overlay.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {

                if (Math.abs(dY) > 0.0f && Math.abs(dX) < 10.0f) {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
//                if (currentRecyclerView != null && currentViewHolder != null) {
//                    Log.d("OnChildDraw", "Current:" + currentViewHolder.getAdapterPosition()+
//                            " Now:" + viewHolder.getAdapterPosition()+ " isActive: "+
//                            isCurrentlyActive);
//                    if (!isCurrentlyActive) {
//                        clearView(currentRecyclerView, currentViewHolder);
//                        currentRecyclerView = null;
//                        currentViewHolder = null;
//                    }
//                }
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                        RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                                        boolean isCurrentlyActive) {
                DescriptionListAdapter.PackageViewHolder touchVH =
                        (DescriptionListAdapter.PackageViewHolder) viewHolder;

                float leaveBehindLength = touchVH.overlay.getWidth();
                final float dir = Math.signum(dX);
                final float absDX = Math.abs(dX);

                Log.d("onChildDrawOver","dX: " + dX + " layout width:" + touchVH.relativeLayout
                        .getWidth());
//                if (absDX < leaveBehindLength) {
                    touchVH.relativeLayout.setTranslationX(dX);
//                } else if (absDX > leaveBehindLength) {
//                    touchVH.relativeLayout.setTranslationX(leaveBehindLength * dir);
//                    currentRecyclerView = recyclerView;
//                    currentViewHolder = viewHolder;
//                }

                if (dir == 0) {
                    touchVH.overlay.setTranslationX(-touchVH.overlay.getWidth());
                } else {
                    float overlayOffset;
                    float layoutPositionLeft = touchVH.relativeLayout.getTranslationX();
                    float layoutPositionRight = layoutPositionLeft + touchVH.relativeLayout.getWidth();
                    if (dir == -1.0) {
                        overlayOffset = layoutPositionRight;
                    } else {
                        overlayOffset = layoutPositionLeft - leaveBehindLength;
                    }

                    touchVH.overlay.setTranslationX(overlayOffset);
                }
                touchVH.overlay.setAlpha(1.0f);

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
                Log.d("TAG", "clearView:");
                super.clearView(recyclerView, viewHolder);
                DescriptionListAdapter.PackageViewHolder touchVH = (DescriptionListAdapter.PackageViewHolder) viewHolder;

                touchVH.overlay.setVisibility(View.INVISIBLE);

            }
        };
    }

    private List<Attraction> CreateAttractionList() {
        List<Attraction> attractions = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
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
