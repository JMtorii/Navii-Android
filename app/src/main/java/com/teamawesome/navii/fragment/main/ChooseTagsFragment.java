package com.teamawesome.navii.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamawesome.navii.NaviiApplication;
import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.ItineraryRecommendActivity;
import com.teamawesome.navii.adapter.TagGridAdapter;
import com.teamawesome.navii.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sjung on 18/11/15.
 */
public class ChooseTagsFragment extends NaviiParallaxFragment {

    @BindView(R.id.tags_picker)
    RecyclerView mTagsGridView;

    private TagGridAdapter mTagGridAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planning_tags, container, false);
        ButterKnife.bind(this, view);

        Observable<List<String>> observable = NaviiApplication.getInstance().getTagsAPI().getTags();
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {
                        // nothing to do here
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ERROR", e.getMessage());
                    }

                    @Override
                    public void onNext(List<String> tags) {
                        mTagGridAdapter = new TagGridAdapter(tags);
                        mTagsGridView.setAdapter(mTagGridAdapter);
                        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                        mTagsGridView.setLayoutManager(gridLayoutManager);
                    }
                });

        return view;
    }

    @Override
    public void nextFunction() {
        Intent itineraryRecommendIntent = new Intent(getContext(), ItineraryRecommendActivity.class);
        if (mTagGridAdapter != null) {
            itineraryRecommendIntent.putStringArrayListExtra(Constants.INTENT_TAGS, new ArrayList<>(mTagGridAdapter.getActiveTags()));
        }
        startActivity(itineraryRecommendIntent);
    }
}
