package com.teamawesome.navii.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.TagGridAdapter;
import com.teamawesome.navii.fragment.main.ItineraryRecommendFragment;
import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.util.Constants;

import java.util.ArrayList;
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
 * Created by sjung on 10/06/16.
 */
public class TagSelectActivity extends NaviiActivity {
    @BindView(R.id.tags_picker)
    RecyclerView mTagsGridView;
//
//    @BindView(R.id.tags_next_button)
//    Button mNextButton;

    private TagGridAdapter mTagGridAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_planning_tags);

        ButterKnife.bind(this);

        final RecyclerView.LayoutManager gridLayoutManager =
                new GridLayoutManager(this, 2);
        Observable<List<String>> observable = tagsAPI.getTags();
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
                        mTagGridAdapter = new TagGridAdapter(tags);
                        mTagsGridView.setAdapter(mTagGridAdapter);
                        mTagsGridView.setLayoutManager(gridLayoutManager);
                    }
                });
    }

//    @OnClick(R.id.tags_next_button)
    public void nextPress() {

        Bundle tagsBundle = new Bundle();

        tagsBundle.putStringArrayList("tags",
                new ArrayList<>(mTagGridAdapter.getSelectedTags()));
        Intent itineraryRecommendIntent = new Intent(this, ItineraryRecommendFragment.class);
        itineraryRecommendIntent.
                putStringArrayListExtra("TAGS", new ArrayList<>(mTagGridAdapter.getSelectedTags()));

        Call<List<Itinerary>> itineraryListCall =
                itineraryAPI.getItineraries(mTagGridAdapter.getSelectedTags());

        itineraryListCall.enqueue(new Callback<List<Itinerary>>() {
            @Override
            public void onResponse(Response<List<Itinerary>> response, Retrofit retrofit) {
                if (response.code() == 200) {
                    List<Itinerary> itineraryList = response.body();

                    ItineraryRecommendFragment itineraryRecommendFragment =
                            ItineraryRecommendFragment.newInstance(itineraryList);
                    switchFragment(
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
