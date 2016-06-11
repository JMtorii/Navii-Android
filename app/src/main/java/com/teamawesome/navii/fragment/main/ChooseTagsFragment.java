package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.TagGridAdapter;
import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.util.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sjung on 18/11/15.
 */
public class ChooseTagsFragment extends NaviiFragment {

    @BindView(R.id.tags_picker)
    RecyclerView mTagsGridView;

    private TagGridAdapter mTagGridAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planning_tags, container, false);
        ButterKnife.bind(this, view);

        Observable<List<String>> observable = parentActivity.tagsAPI.getTags();
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ERROR", e.getMessage());
                    }

                    @Override
                    public void onNext(List<String> tags) {
                        mTagGridAdapter = new TagGridAdapter(getContext(), tags);
                        mTagsGridView.setAdapter(mTagGridAdapter);
                        RecyclerView.LayoutManager gridLayoutManager =
                                new GridLayoutManager(getContext(), 2);
                        mTagsGridView.setLayoutManager(gridLayoutManager);
                    }
                });

        return view;
    }

    public void nextPress() {

        Call<List<Itinerary>> itineraryListCall =
                parentActivity.itineraryAPI.getItineraries(mTagGridAdapter.getSelectedTags());

        itineraryListCall.enqueue(new Callback<List<Itinerary>>() {
            @Override
            public void onResponse(Response<List<Itinerary>> response, Retrofit retrofit) {
                if (response.code() == 200) {
                    List<Itinerary> itineraryList = response.body();
                    ItineraryRecommendFragment itineraryRecommendFragment =
                            ItineraryRecommendFragment.newInstance(itineraryList);
                    parentActivity.switchFragment(
                            itineraryRecommendFragment,
                            Constants.NO_ANIM,
                            Constants.NO_ANIM,
                            Constants.ITINERARY_RECOMMEND_FRAGMENT,
                            true,
                            true,
                            true
                    );
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("failed", t.toString());
            }
        });
    }

}
