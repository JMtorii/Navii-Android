package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.TagsGridAdapter;
import com.teamawesome.navii.server.api.TagsAPI;
import com.teamawesome.navii.util.NaviiPreferenceData;

import java.util.List;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sjung on 18/11/15.
 */
public class ChooseTagsFragment extends NaviiFragment {

    private Button mNextButton;
    private GridView mTagsGridView;
    private TagsGridAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planning_tags, container, false);

        mTagsGridView = (GridView) view.findViewById(R.id.tags_picker);

        Retrofit retrofitObservable = new Retrofit.Builder()
                .baseUrl(NaviiPreferenceData.getIPAddress())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        TagsAPI tagsAPI= retrofitObservable.create(TagsAPI.class);

        Observable<List<String>> observable = tagsAPI.getTags();
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {
                        // nothing to do here
                    }

                    @Override
                    public void onError(Throwable e) {
                        // nothing to do here
                    }

                    @Override
                    public void onNext(List<String> tags) {
                        TagsGridAdapter adapter =
                                new TagsGridAdapter(getContext(), R.layout.tags_selectable_grid_item, tags);
                        mTagsGridView.setAdapter(adapter);
                        mAdapter = adapter;
                    }
                });

//        mNextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Call<List<Itinerary>> itineraryListCall =
//                        parentActivity.itineraryAPI.getItineraries(mAdapter.getSelectedTags());
//
//                itineraryListCall.enqueue(new Callback<List<Itinerary>>() {
//                    @Override
//                    public void onResponse(Response<List<Itinerary>> response, Retrofit retrofit) {
//                        if (response.code() == 200) {
//                            List<Itinerary> itineraryList = response.body();
//                            ItineraryRecommendFragment itineraryRecommendFragment =
//                                    ItineraryRecommendFragment.newInstance(itineraryList);
//                            parentActivity.switchFragment(
//                                    itineraryRecommendFragment,
//                                    Constants.NO_ANIM,
//                                    Constants.NO_ANIM,
//                                    Constants.ITINERARY_RECOMMEND_FRAGMENT,
//                                    true,
//                                    true,
//                                    true
//                            );
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t) {
//                        Log.e("failed", t.toString());
//                    }
//                });
//
//            }
//        });
        return view;
    }
}
