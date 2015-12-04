package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.anton46.collectionitempicker.CollectionPicker;
import com.anton46.collectionitempicker.Item;
import com.anton46.collectionitempicker.OnItemClickListener;
import com.teamawesome.navii.R;
import com.teamawesome.navii.server.api.UserPreferenceAPI;
import com.teamawesome.navii.server.model.UserPreference;
import com.teamawesome.navii.util.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by sjung on 18/11/15.
 */
public class ChooseTagsFragment extends MainFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ChooseTagsFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_planning_tags, container, false);

        Button button = (Button) view.findViewById(R.id.tags_next_button);

        Item[] items = new Item[]{
                new Item("able", "able"),
                new Item("abnormal", "abnormal"),
                new Item("absent-minded", "absent-minded"),
                new Item("aboveaverage", "above average"),
                new Item("adventurous", "adventurous"),
                new Item("affectionate", "affectionate"),
                new Item("agile", "agile"),
                new Item("agreeable", "agreeable"),
                new Item("alert", "alert"),
                new Item("amazing", "amazing"),
                new Item("ambitious", "ambitious"),
                new Item("amiable", "amiable"),
                new Item("amusing", "amusing"),
                new Item("analytical", "analytical"),
                new Item("angelic", "angelic"),
                new Item("apathetic", "apathetic"),
                new Item("apprehensive", "apprehensive"),
                new Item("ardent", "ardent"),
                new Item("artificial", "artificial"),
                new Item("artistic", "artistic")};

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_URL)    // THIS ONLY WORKS ON JUN'S CASE
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        UserPreferenceAPI userPreferenceAPI = retrofit.create(UserPreferenceAPI.class);

        List<Item> tags = new ArrayList<>();

        for (Item item : items) {
            tags.add(item);
        }

        CollectionPicker tagsPicker = (CollectionPicker) view.findViewById(R.id.tags_picker);

        tagsPicker.setItems(tags);
        tagsPicker.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(Item item, int position) {
                Log.d("OnClick", item.text);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("ChooseTagsFragment", "onClick()");

                parentActivity.switchFragment(new ItineraryRecommendFragment(), Constants
                        .NO_ANIM, Constants.NO_ANIM, Constants.ITINERARY_RECOMMEND_FRAGMENT,
                        true, true, true);
            }
        });
        return view;
    }
}
