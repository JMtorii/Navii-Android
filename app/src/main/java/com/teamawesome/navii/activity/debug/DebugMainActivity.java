 package com.teamawesome.navii.activity.debug;

 import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.teamawesome.navii.NaviiApplication;
import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.ItineraryScheduleActivity;
 import com.teamawesome.navii.activity.LoginActivity;
 import com.teamawesome.navii.activity.MainActivity;
 import com.teamawesome.navii.activity.OnboardingActivity;
 import com.teamawesome.navii.activity.PreferencesActivity;
import com.teamawesome.navii.activity.UploadImageTestActivity;
import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.NaviiPreferenceData;

// TODO: change this to AppCompatActivity for more flexibility
public class DebugMainActivity extends ListActivity {
    String[] mTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // change this to the real server address
        NaviiPreferenceData.setIPAddress(Constants.SERVER_URL);
        mTitles = getApplicationContext().getResources().getStringArray(R.array.debug_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                mTitles
        );
        setListAdapter(adapter);
        setTitle("Debug Main Activity");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_debug_main, menu);
        return true;
    }

    @Override
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);

        if (id == 0) {              // Actual application
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
        } else if (id == 1) {       // Upload profile image test
            Intent imageUploadTestActivity = new Intent(this, UploadImageTestActivity.class);
            startActivity(imageUploadTestActivity);
        } else if (id == 2) {       // Heart and Soul Details Activity
            Intent preferenceActivity = new Intent(this, PreferencesActivity.class);
            startActivity(preferenceActivity);
        } else if (id == 3) {       // Itinerary Schedule
            Intent itineraryScheduleActivity = new Intent(this, ItineraryScheduleActivity.class);
            Itinerary itinerary = new Itinerary();
            NaviiApplication.getInstance().getBus().send(itinerary);
            startActivity(itineraryScheduleActivity);
        } else if (id == 4) {       // Onboarding
            Intent onboardingActivity = new Intent(this, OnboardingActivity.class);
            startActivity(onboardingActivity);
        } else if (id == 5) {       // Login
            Intent loginActivity = new Intent(this, LoginActivity.class);
            startActivity(loginActivity);
        }
    }
}
