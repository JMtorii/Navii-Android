package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.anton46.collectionitempicker.CollectionPicker;
import com.anton46.collectionitempicker.Item;
import com.teamawesome.navii.R;
import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    private Button mNextButton;
    private CollectionPicker mTagsPicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planning_tags, container, false);
        mNextButton = (Button) view.findViewById(R.id.tags_next_button);
        mTagsPicker = (CollectionPicker) view.findViewById(R.id.tags_picker);

        Observable<List<String>> observable = parentActivity.tagsAPI.getTags();
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
                        mTagsPicker.clearItems();
                        List<Item> tagsList = new ArrayList<>();
                        for (String tag : tags) {
                            tagsList.add(new Item(tag, tag));
                        }
                        mTagsPicker.setItems(tagsList);
                        mTagsPicker.drawItemView();
                    }
                });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<String> checkedSet = mTagsPicker.getCheckedItems().keySet();
                List<String> checkedList = new ArrayList<>(checkedSet);
                Call<List<Itinerary>> itineraryListCall =
                        parentActivity.itineraryAPI.getItineraries(checkedList);
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
                        Log.e("failed", t.getMessage());
                    }
                });

            }
        });
        return view;
    }
}
