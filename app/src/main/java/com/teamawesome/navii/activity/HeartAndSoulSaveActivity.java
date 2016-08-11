package com.teamawesome.navii.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.widget.Toast;

import com.teamawesome.navii.R;
import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.util.AnalyticsManager;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.RestClient;
import com.teamawesome.navii.util.ToolbarConfiguration;
import com.teamawesome.navii.util.ViewUtilities;
import com.teamawesome.navii.views.MainLatoButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by JMtorii on 16-07-17.
 */
public class HeartAndSoulSaveActivity extends NaviiToolbarActivity {
    @BindView(R.id.heart_and_soul_save_name_layout)
    TextInputLayout titleLayout;

    @BindView(R.id.heart_and_soul_save_name_text)
    TextInputEditText itineraryTitle;

    @BindView(R.id.itinerary_save_button)
    MainLatoButton itinerarySave;

    private List<Itinerary> itineraries;

    private ProgressDialog progressDialog;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        ViewUtilities.setTypefaceToInputLayout(this, titleLayout, "fonts/Lato-Regular.ttf");

        itineraries = getIntent().getParcelableArrayListExtra(Constants.INTENT_ITINERARIES);
    }

    @OnClick(R.id.itinerary_save_button)
    public void onClick(){
        String title = itineraryTitle.getText().toString();
        if (title.trim().isEmpty()){
            Toast.makeText(this, "Put a name", Toast.LENGTH_SHORT).show();
        } else {
            Observable<Void> saveCall = RestClient.itineraryAPI.saveItineraries(itineraries, title);
            saveCall.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Void>() {

                        @Override
                        public void onCompleted() {
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("Error", e.getMessage());
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onNext(Void aVoid) {
                            Toast.makeText(HeartAndSoulSaveActivity.this, "Trip Saved!", Toast.LENGTH_LONG).show();
                            itineraryTitle.setEnabled(false);
                            itinerarySave.setText(R.string.already_saved);
                            itinerarySave.setClickable(false);
                            Intent savedTripsActivity = new Intent(HeartAndSoulSaveActivity.this, SavedTripsActivity.class);
                            Activity activity = HeartAndSoulSaveActivity.this;
                            activity.startActivity(savedTripsActivity);
                            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    });
            progressDialog = ProgressDialog.show(this, "Building the perfect trip", "Loading itineraries...");
            AnalyticsManager.getMixpanel().track("HeartAndSoulSaveActivity - onCreate");
        }
    }
}
