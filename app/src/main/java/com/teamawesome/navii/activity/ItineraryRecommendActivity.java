package com.teamawesome.navii.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.ItineraryRecommendListAdapter;
import com.teamawesome.navii.server.model.Itinerary;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by sjung on 11/06/16.
 */
public class ItineraryRecommendActivity extends NaviiActivity {

    @BindView(R.id.itineraryList)
    RecyclerView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        List<String> tags = getIntent().getStringArrayListExtra("tags");

        Call<List<Itinerary>> itineraryListCall =
                itineraryAPI.getItineraries(tags);

        setContentView(R.layout.fragment_itinerary_recommend);

        final Context context = this;
        itineraryListCall.enqueue(new Callback<List<Itinerary>>() {
            @Override
            public void onResponse(Response<List<Itinerary>> response, Retrofit retrofit) {
                if (response.code() == 200) {
                    ItineraryRecommendListAdapter adapter =
                            new ItineraryRecommendListAdapter(context, response.body());

                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
