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
import com.teamawesome.navii.server.api.TagsAPI;
import com.teamawesome.navii.util.Constants;

import java.util.ArrayList;
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
public class ChooseTagsFragment extends MainFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_planning_tags, container, false);
        Button button = (Button) view.findViewById(R.id.tags_next_button);
        final CollectionPicker tagsPicker = (CollectionPicker) view.findViewById(R.id.tags_picker);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        Observable<List<String>> observable = retrofit.create(TagsAPI.class).getTags();
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<String> tags) {
                        tagsPicker.clearItems();
                        List<Item> tagsList = new ArrayList<>();
                        for (String tag : tags) {
                            tagsList.add(new Item(tag, tag));
                        }
                        tagsPicker.setItems(tagsList);
                        tagsPicker.drawItemView();

                    }
                });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("ChooseTagsFragment", "onClick()");

                parentActivity.switchFragment(
                        new ItineraryRecommendFragment(),
                        Constants.NO_ANIM,
                        Constants.NO_ANIM,
                        Constants.ITINERARY_RECOMMEND_FRAGMENT,
                        true,
                        true,
                        true
                );
            }
        });
        return view;
    }
}
