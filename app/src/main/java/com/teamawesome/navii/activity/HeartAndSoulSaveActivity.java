package com.teamawesome.navii.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.widget.Toast;

import com.teamawesome.navii.R;
import com.teamawesome.navii.server.model.Attraction;
import com.teamawesome.navii.server.model.HeartAndSoulPackage;
import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.ToolbarConfiguration;
import com.teamawesome.navii.util.ViewUtilities;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JMtorii on 16-07-17.
 */
public class HeartAndSoulSaveActivity extends NaviiToolbarActivity {

    private List<Itinerary> itineraries;
    private List<Attraction> attractions;
    private List<Attraction> restaurants;

    @Override
    public ToolbarConfiguration getToolbarConfiguration() {
        return ToolbarConfiguration.HeartAndSoulSave;
    }

    @Override
    public void onLeftButtonClick() {
        // Nothing to do here
    }

    @Override
    public void onRightButtonClick() {
        onModalBackPressed();
    }

    @BindView(R.id.heart_and_soul_save_name_layout)
    TextInputLayout titleLayout;

    @BindView(R.id.heart_and_soul_save_name_text)
    TextInputEditText itineraryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        ViewUtilities.setTypefaceToInputLayout(this, titleLayout, "fonts/Lato-Regular.ttf");

        itineraries = getIntent().getParcelableArrayListExtra(Constants.INTENT_ITINERARIES);
        attractions = getIntent().getParcelableArrayListExtra(Constants.INTENT_EXTRA_ATTRACTION_LIST);
        restaurants = getIntent().getParcelableArrayListExtra(Constants.INTENT_EXTRA_ATTRACTION_LIST);
    }

    @OnClick(R.id.itinerary_save_button)
    public void onClick(){
        String title = itineraryTitle.getText().toString();
        if (title.trim().isEmpty()){
            Toast.makeText(this, "Put a name", Toast.LENGTH_SHORT).show();
        }
        else{
            Itinerary [][] test = new Itinerary[1][itineraries.size()];
            for (int i = 0; i < itineraries.size(); i++){
                test[0][i] = itineraries.get(i);
            }
            HeartAndSoulPackage hsPackage = new HeartAndSoulPackage.Builder().itineraries(test)
                                             .extraAttractions(attractions)
                                             .extraAttractions(restaurants).build();
            hsPackage.packageNickname = title;
            //TODO Persist with user?
            Log.d("Attractions: ", String.valueOf(hsPackage.getExtraAttractions().size()));
//            boolean isDuplicate = false;
//            for (Itinerary i : SavedTripsActivity.savedItineraries){
//                isDuplicate = saved.getItineraryNickname().equals(i.getItineraryNickname());
//            }
//            if (!isDuplicate){
//                SavedTripsActivity.savedItineraries.add(saved);
//            }
//            else{
//                Toast.makeText(this, "Name Taken", Toast.LENGTH_SHORT).show();
//            }
        }
    }
}
